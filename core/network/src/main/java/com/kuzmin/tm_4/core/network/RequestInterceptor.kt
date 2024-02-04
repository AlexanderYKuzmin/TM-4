package com.kuzmin.tm_4.core.network

import okhttp3.Interceptor
import okhttp3.Response

import javax.inject.Inject

class RequestInterceptor @Inject constructor (
) : Interceptor {
    private var token: String? = null

    fun setToken(token: String) {
        this.token = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        request.newBuilder()
            .removeHeader("Content-Type")
            .addHeader("Authorization", "Bearer $token")
            .build()
        println("Token: $token")
        println("Outgoing request to: ${request.url}")
        println("Outgoing headers: ${request.headers}")
        return chain.proceed(request)
    }
}