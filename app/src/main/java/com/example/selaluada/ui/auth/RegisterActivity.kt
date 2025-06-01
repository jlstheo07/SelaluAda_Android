package com.example.selaluada.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.selaluada.MainActivity
import com.example.selaluada.data.model.RegisterRequest
import com.example.selaluada.data.model.RegisterResponse
import com.example.selaluada.data.network.AuthApiClient
import com.example.selaluada.data.service.AuthApiService
import com.example.selaluada.databinding.ActivityRegisterBinding
import com.example.selaluada.util.SharedPreferenceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle safe area padding
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Register ditekan
        binding.btnRegister.setOnClickListener {
            val fullName = binding.namalengkapreg.text.toString().trim()
            val username = binding.usernamereg.text.toString().trim()
            val email = binding.emailreg.text.toString().trim()
            val password = binding.passreg.text.toString()
            val confirmPassword = binding.passconreg.text.toString()

            if (fullName.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty()
                && password.isNotEmpty() && confirmPassword.isNotEmpty()
            ) {
                if (password == confirmPassword) {
                    registerUser(fullName, username, email, password)
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Pindah ke halaman login
        binding.tvSignUp.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun registerUser(fullName: String, username: String, email: String, password: String) {
        val request = RegisterRequest(
            username = username,
            password = password,
            email = email,
            nama_lengkap = fullName
        )

        val apiService = AuthApiClient.getInstance(this).create(AuthApiService::class.java)

        apiService.register(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.token

                    if (!token.isNullOrEmpty()) {
                        SharedPreferenceUtil.saveAuthToken(this@RegisterActivity, token)
                        Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Registration successful (no token)", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "Registration failed: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
