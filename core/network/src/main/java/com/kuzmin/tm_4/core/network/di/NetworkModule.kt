package com.kuzmin.tm_4.core.network.di

import com.kuzmin.tm_4.core.network.ApiService
import com.kuzmin.tm_4.core.network.RequestInterceptor
import com.kuzmin.tm_4.core.network.TokenContainer
import com.kuzmin.tm_4.core.network.UserApiService
import com.kuzmin.tm_4.core.network.di.qualifiers.SiteQualifier
import com.kuzmin.tm_4.core.network.di.qualifiers.UserQualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "http://176.119.159.44/api/v1/"
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @SiteQualifier
    fun provideOkHttpClient(requestInterceptor: RequestInterceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(requestInterceptor)
            .build()
    }

    @Provides
    @UserQualifier
    fun provideUserOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }

    @Provides
    fun provideApiService(@SiteQualifier okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    @Provides
    fun provideUserApiService(@UserQualifier okHttpClient: OkHttpClient): UserApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(UserApiService::class.java)
    }

    /*@Singleton
    @Provides
    fun provideAppToken(): String {
        return TokenContainer.appToken
    }*/
}