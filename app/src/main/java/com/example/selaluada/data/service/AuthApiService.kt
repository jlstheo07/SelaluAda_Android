package com.example.selaluada.data.service

import com.example.selaluada.data.model.LogoutResponse
import com.example.selaluada.data.model.LoginRequest
import com.example.selaluada.data.model.LoginResponse
import com.example.selaluada.data.model.LogoutRequest
import com.example.selaluada.data.model.RegisterRequest
import com.example.selaluada.data.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface  AuthApiService {
    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("auth/logout")
    fun logout(@Body request: LogoutRequest? = null): Call<Void>

    @POST("api/users/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

}