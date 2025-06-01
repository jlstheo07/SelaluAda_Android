package com.example.selaluada.data.service


import com.example.selaluada.data.model.CreatePengajuanRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PengajuanApiService {
    @POST("api/pengajuan")
    fun createPengajuan(
        @Body request: CreatePengajuanRequest
    ): Call<Void>
}
