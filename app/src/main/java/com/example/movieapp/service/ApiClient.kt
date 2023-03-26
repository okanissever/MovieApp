package com.example.movieapp.service

import com.example.movieapp.common.Constants.API_KEY
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

class ApiClient {

    fun getClient(): OkHttpClient {
        val apiKeyInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val originalHttpUrl = originalRequest.url

                val newHttpUrl = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val newRequest = originalRequest.newBuilder()
                    .url(newHttpUrl)
                    .build()
                return chain.proceed(newRequest)
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()
    }
}