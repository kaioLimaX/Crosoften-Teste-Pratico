package com.skymob.crosoftenteste.data.remote.dto.user

data class RegisterResponse(
    val id: Int,
    val name: String,
    val email: String,
    val createdAt: String,
    val updatedAt: String
)