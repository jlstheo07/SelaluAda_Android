package com.example.selaluada.data.model

data class LoginResponse(
    val token: String,
    val role_id: String,
    val username: String,
    val role: String,
    val customerId: String
)