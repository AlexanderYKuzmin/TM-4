package com.kuzmin.tm_4.core.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private const val BASE_URL = "http://176.119.159.44/api/v1/"
    //const val BASE_IMAGE_URL = "https://cryptocompare.com"

    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(RequestInterceptor)
        .build()

    /*private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-ss")
        .create()*/
    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}