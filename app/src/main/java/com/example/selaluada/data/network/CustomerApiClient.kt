package com.example.selaluada.data.network

import android.content.Context
import android.util.Log
import com.example.selaluada.data.service.CustomerApiService
import com.example.selaluada.util.SharedPreferenceUtil
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object CustomerApiClient {
    private const val BASE_URL = "https://6fee-140-213-33-135.ngrok-free.app/"

    private var retrofitInstance: Retrofit? = null

    private fun getHttpClient(context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        // bisa pakai interceptor seperti AuthApiClient juga kalau perlu token
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()
                    .addHeader("Content-Type", "application/json")

                // Tambahkan Authorization header jika token tersedia
                val token = SharedPreferenceUtil.getAuthToken(context)
                Log.d("AuthApiClient", "Token: $token")
                if (!token.isNullOrEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    fun getInstance(context: Context): CustomerApiService {
        if (retrofitInstance == null) {
            retrofitInstance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitInstance!!.create(CustomerApiService::class.java)
    }
}
