package com.example.selaluada.data.network


import com.example.selaluada.data.model.LoginRequest
import com.example.selaluada.data.model.LoginResponse
import com.example.selaluada.data.model.RegisterRequest
import com.example.selaluada.data.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Definisikan interface untuk API endpoint
interface AuthApi {
    @POST("/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/auth/register")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

}