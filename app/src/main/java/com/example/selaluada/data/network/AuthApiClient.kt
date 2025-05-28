package com.example.selaluada.data.network

import android.content.Context
import com.example.selaluada.util.SharedPreferenceUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log


object AuthApiClient {
    private const val BASE_URL = "https://6b07-112-215-235-14.ngrok-free.app"

    private var retrofitInstance: Retrofit? = null

    // Membuat HTTP Client dengan interceptor untuk logging dan header Authorization
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

    // Mendapatkan Retrofit instance (singleton)
    fun getInstance(context: Context): Retrofit {
        if (retrofitInstance == null) {
            retrofitInstance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient(context))
                .build()
        }
        return retrofitInstance!!
    }
}
