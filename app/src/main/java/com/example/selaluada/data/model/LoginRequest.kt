package com.example.selaluada.data.model

data class LoginRequest(
    val usernameOrEmail: String,
    val password: String,
    val fcmToken: String? = null
)
