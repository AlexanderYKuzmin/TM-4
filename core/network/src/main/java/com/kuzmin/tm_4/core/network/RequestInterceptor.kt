package com.kuzmin.tm_4.core.network

import com.kuzmin.tm_4.core.network.TokenContainer.token
import okhttp3.Interceptor
import okhttp3.Response

object RequestInterceptor : Interceptor {
    //var token: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        println("Outgoing request to: ${request.url()}")
        println("Outgoing request to: ${request.headers()}")
        return chain.proceed(request)
    }
}