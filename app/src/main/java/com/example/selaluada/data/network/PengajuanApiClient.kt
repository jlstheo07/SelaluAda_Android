package com.example.selaluada.data.network

import android.content.Context
import android.util.Log
import com.example.selaluada.data.service.PengajuanApiService
import com.example.selaluada.util.SharedPreferenceUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PengajuanApiClient {

    private const val BASE_URL = " https://c5b0-140-213-7-34.ngrok-free.app/"

    private var retrofitInstance: Retrofit? = null

    private fun getHttpClient(context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()
                    .addHeader("Content-Type", "application/json")

                val token = SharedPreferenceUtil.getAuthToken(context)
                Log.d("PengajuanApiClient", "Token: $token")
                if (!token.isNullOrEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    fun getInstance(context: Context): PengajuanApiService {
        if (retrofitInstance == null) {
            retrofitInstance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitInstance!!.create(PengajuanApiService::class.java)
    }
}
