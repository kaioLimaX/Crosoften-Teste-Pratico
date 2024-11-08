package com.skymob.crosoftenteste.data.remote.repository

import com.skymob.crosoftenteste.data.remote.api.ApiService
import com.skymob.crosoftenteste.data.remote.dto.user.LoginRequest
import com.skymob.crosoftenteste.data.remote.dto.user.LoginResponse
import com.skymob.crosoftenteste.data.remote.dto.user.RegisterRequest
import com.skymob.crosoftenteste.data.remote.dto.user.RegisterResponse
import com.skymob.crosoftenteste.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(private val apiService: ApiService) : UserRepository {

    override suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Flow<Result<RegisterResponse>> = flow {
        val userRequest = RegisterRequest(name, email, password, confirmPassword)

        val response = apiService.registerUser(userRequest)

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                emit(Result.success(body))
            } else {
                emit(Result.failure(Exception("Response body is null")))
            }
        } else {
            val errorMessage = response.errorBody()?.string() ?: "Erro desconhecido"
            emit(Result.failure(Exception(errorMessage)))
        }
    }.catch { e ->
        emit(Result.failure(e))

    }

    override suspend fun login(credential: String, password: String): Flow<Result<LoginResponse>> =
        flow {
            val user = LoginRequest(credential, password)

            val response = apiService.loginUser(user)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.success(body))
                } else {
                    emit(Result.failure(Exception("Response body is null")))
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Erro desconhecido"
                emit(Result.failure(Exception(errorMessage)))

            }
        }.catch { e ->
            emit(Result.failure(e))
        }
}