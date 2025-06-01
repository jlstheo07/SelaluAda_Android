package com.example.selaluada.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CustomerApiService {
    @Multipart
    @POST("api/customers/register") // sesuaikan endpoint backend kamu
    fun registerCustomer(
        @Part("data") data: RequestBody,
        @Part fotoKtp: MultipartBody.Part?
    ): Call<Void>
}
