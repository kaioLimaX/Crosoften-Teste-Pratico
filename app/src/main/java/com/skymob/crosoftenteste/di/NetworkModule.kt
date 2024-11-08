package com.skymob.crosoftenteste.di

import com.skymob.crosoftenteste.data.remote.api.interceptors.AuthInterceptor
import com.skymob.crosoftenteste.util.Constants
import com.skymob.crosoftenteste.util.SharedPreferencesManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideOkHttpClient(sharedPreferencesManager: SharedPreferencesManager): OkHttpClient {
    // Interceptor para log das requisições
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY

    }

    // Interceptor para autenticação
    val authInterceptor = AuthInterceptor(sharedPreferencesManager)

    return OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
}

fun provideRetrofit(sharedPreferencesManager: SharedPreferencesManager): Retrofit {
    // Configuração do Retrofit com OkHttpClient configurado
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(provideOkHttpClient(sharedPreferencesManager))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}