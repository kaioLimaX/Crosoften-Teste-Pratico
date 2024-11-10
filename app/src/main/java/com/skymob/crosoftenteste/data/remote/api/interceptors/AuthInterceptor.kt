package com.skymob.crosoftenteste.data.remote.api.interceptors

import com.skymob.crosoftenteste.util.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sharedPreferencesManager: SharedPreferencesManager) : Interceptor {

    // Flag que controla se o token será adicionado
    private var shouldAddAuthHeader: Boolean = true

    // Método para desabilitar a adição do token
    fun disableAuthHeader() {
        shouldAddAuthHeader = false
    }

    // Método para habilitar a adição do token
    fun enableAuthHeader() {
        shouldAddAuthHeader = true
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPreferencesManager.getToken()

        // Cria um builder para a requisição
        val requestBuilder = chain.request().newBuilder()

        // Se a flag estiver habilitada, adiciona o token no cabeçalho
        if (shouldAddAuthHeader && token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        // Continua com a requisição
        return chain.proceed(requestBuilder.build())
    }
}