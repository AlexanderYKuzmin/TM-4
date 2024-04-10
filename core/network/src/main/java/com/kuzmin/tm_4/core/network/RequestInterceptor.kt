package com.kuzmin.tm_4.core.network

import okhttp3.Interceptor
import okhttp3.Response

import javax.inject.Inject

class RequestInterceptor @Inject constructor (
) : Interceptor {
    var token: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        request.newBuilder()
            .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJ1c2VybmFtZSI6ImFkbWluIiwiZXhwIjoxNzA3MjMzMDExLCJlbWFpbCI6ImFkbWluQHRvd2Vycy5sYXduZG9nLmNvbSJ9.gb72GO8B0iYHLx6zkxdcr5qJofiOzstc3cfXKwHmC8s")
            .build()
        println("Token: $token")
        println("Outgoing request to: ${request.url}")
        println("Outgoing headers: ${request.headers}")
        return chain.proceed(request)
    }
}