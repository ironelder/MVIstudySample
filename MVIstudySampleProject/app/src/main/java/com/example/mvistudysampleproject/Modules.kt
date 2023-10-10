package com.example.mvistudysampleproject

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    fun provideBaseUrl() = "https://api.themoviedb.org/3"

    @Singleton
    @Provides
    fun provideOkHttpClient(headerInterceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .header("Authorization", "Bearer e17c2715face752d9ed1a2a0054aa7d6")
                .header("accept", "application/json")
                .build()
            chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}