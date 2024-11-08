package com.skymob.crosoftenteste.domain.usecases.auth

import com.skymob.crosoftenteste.data.remote.dto.user.RegisterResponse
import com.skymob.crosoftenteste.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        confirmPassword: String) = userRepository.registerUser(name, email, password, confirmPassword)
}