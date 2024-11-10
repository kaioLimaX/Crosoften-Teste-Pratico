package com.skymob.crosoftenteste.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPreferencesManager(context: Context, private val gson: Gson) {

    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    // Função para salvar o token
    fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }

    // Função para recuperar o token
    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }
    // Função para salvar o token
    fun keepLogin() {
        prefs.edit().putBoolean("keepLogin", true).apply()
    }
    fun checkKeepLogin(): Boolean? {
        return prefs.getBoolean("keepLogin", false)
    }

    // Função para salvar um objeto
    fun <T> saveObject(key: String, obj: T) {
        val json = gson.toJson(obj) // Converte o objeto para JSON usando o Gson injetado
        prefs.edit().putString(key, json).apply()
    }

    // Função para recuperar um objeto
    fun <T> getObject(key: String, clazz: Class<T>): T? {
        val json = prefs.getString(key, null)
        return if (json != null) {
            gson.fromJson(json, clazz) // Converte o JSON de volta para o objeto usando o Gson injetado
        } else {
            null
        }
    }

    // Função para recuperar um limpar sessão
    fun clearSession() {
        prefs.edit().clear().apply() // Limpa todas as entradas
    }
}