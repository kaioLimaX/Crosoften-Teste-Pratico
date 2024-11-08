package com.skymob.crosoftenteste.domain.repository

import com.skymob.crosoftenteste.data.remote.dto.user.LoginResponse
import com.skymob.crosoftenteste.data.remote.dto.user.RegisterResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(name: String, email: String, password: String, confirmPassword: String): Flow<Result<RegisterResponse>>
    suspend fun login(credential: String, password: String): Flow<Result<LoginResponse>>
}