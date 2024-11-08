package com.skymob.crosoftenteste.data.remote.api.interceptors

import com.skymob.crosoftenteste.util.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sharedPreferencesManager: SharedPreferencesManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPreferencesManager.getToken() // Recupera o token do SharedPreferences
        //val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTIsImlhdCI6MTczMDQ3NTkwMiwiZXhwIjoxNzMzMDY3OTAyfQ.2eEiG9HJNjkdy0GvXQE5GTFpnFMkXNoKsA4xSTRLaWE"

        val requestBuilder = chain.request().newBuilder()
        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}