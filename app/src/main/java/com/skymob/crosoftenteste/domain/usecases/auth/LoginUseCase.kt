package com.skymob.crosoftenteste.domain.usecases.auth

import com.skymob.crosoftenteste.domain.repository.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(credential: String, password: String) = userRepository.login(credential, password)
}