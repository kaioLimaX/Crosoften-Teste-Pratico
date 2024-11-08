package com.skymob.crosoftenteste.data.remote.dto.user

data class LoginRequest(
    val credential: String,
    val password: String
)