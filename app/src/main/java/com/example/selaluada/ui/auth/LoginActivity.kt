package com.example.selaluada.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.selaluada.MainActivity
import com.example.selaluada.data.model.LoginRequest
import com.example.selaluada.data.model.LoginResponse
import com.example.selaluada.data.network.AuthApiClient
import com.example.selaluada.data.service.AuthApiService
import com.example.selaluada.databinding.ActivityLoginBinding
import com.example.selaluada.util.SharedPreferenceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Jika sudah login, langsung redirect ke MainActivity
        if (SharedPreferenceUtil.isLoggedIn(this)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menyesuaikan padding agar tidak ketimpa status bar
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Login
        binding.btnLogin.setOnClickListener {
            val username = binding.etTextUsername.text.toString().trim()
            val password = binding.etTextPassword.text.toString().trim()

            if (username.isEmpty()) {
                Toast.makeText(this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            login(username, password)
        }

        // Redirect ke halaman register
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(username: String, password: String) {
        val request = LoginRequest(username, password)
        val apiService = AuthApiClient.getInstance(this).create(AuthApiService::class.java)

        apiService.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!

                    val token = loginResponse.token
                    if (token.isNullOrEmpty()) {
                        Toast.makeText(this@LoginActivity, "Token kosong, login gagal", Toast.LENGTH_SHORT).show()
                        return
                    }

                    Log.d("LoginActivity", "Login success: Token=$token, Username=${loginResponse.username}")

                    // Simpan token ke SharedPreferences
                    SharedPreferenceUtil.saveAuthToken(this@LoginActivity, token)

                    Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT).show()

                    // Navigasi ke MainActivity
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    Log.e("LoginActivity", "Login gagal: Code=${response.code()}, Message=${response.message()}")
                    Toast.makeText(this@LoginActivity, "Login gagal: ${response.message()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginActivity", "Login error: ${t.message}", t)
                Toast.makeText(this@LoginActivity, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
