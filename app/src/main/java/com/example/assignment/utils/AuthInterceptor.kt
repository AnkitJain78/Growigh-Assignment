package com.example.assignment.utils

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Client-ID 4Cyki7zs6Y6g942CmHfvLVsHXQKocNKDPYLEU0hB2Dc")
        return chain.proceed(request.build())
    }
}