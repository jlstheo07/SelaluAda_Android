package com.example.selaluada.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.selaluada.R
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText


class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var etOldPassword: TextInputEditText
    private lateinit var etNewPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var btnChangePassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        etOldPassword = findViewById(R.id.etOldPassword)
        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnChangePassword = findViewById(R.id.btnChangePassword)

        btnChangePassword.setOnClickListener {
            val oldPassword = etOldPassword.text.toString()
            val newPassword = etNewPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (oldPassword.isBlank() || newPassword.isBlank() || confirmPassword.isBlank()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword != confirmPassword) {
                Toast.makeText(this, "New password and confirm password must match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Integrasi ke API backend
            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
        }
    }
}
