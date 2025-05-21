package com.example.selaluada.data.service

import com.example.selaluada.data.model.LoginRequest
import com.example.selaluada.data.model.LoginResponse
import com.example.selaluada.data.model.RegisterRequest
import com.example.selaluada.data.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface  AuthApiService {
    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/auth/registerAkunCustomer")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

}