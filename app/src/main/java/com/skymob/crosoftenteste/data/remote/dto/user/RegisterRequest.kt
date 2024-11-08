package com.skymob.crosoftenteste.data.remote.dto.user

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)