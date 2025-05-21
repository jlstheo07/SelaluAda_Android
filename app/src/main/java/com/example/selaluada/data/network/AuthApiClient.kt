package com.example.selaluada.data.network

import android.content.Context
import com.example.selaluada.util.SharedPreferenceUtil

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AuthApiClient {
    private const val BASE_URL = " https://421c-140-213-35-22.ngrok-free.app/"

    // Membuat interceptor untuk logging HTTP request dan response
    private fun getHttpClient(context: Context): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")


                val token = SharedPreferenceUtil.getAuthToken(context)
                if(!token.isNullOrEmpty()){
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    // Membuat instance Retrofit
    fun getInstance(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient(context))
            .build()
    }
}