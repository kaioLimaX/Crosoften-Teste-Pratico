package com.skymob.crosoftenteste.data.remote.repository

import com.skymob.crosoftenteste.data.remote.api.ApiService
import com.skymob.crosoftenteste.data.remote.api.interceptors.AuthInterceptor
import com.skymob.crosoftenteste.data.remote.models.getFakeLoginRequest
import com.skymob.crosoftenteste.data.remote.models.getFakeLoginResponse
import com.skymob.crosoftenteste.data.remote.models.getFakeRegisterRequest
import com.skymob.crosoftenteste.data.remote.models.getFakeRegisterResponse
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import retrofit2.Response
import kotlin.test.Test


class UserRepositoryImplTest {

    private lateinit var userRepository: UserRepositoryImpl
    private val apiService: ApiService = mockk() // Mockando ApiService
    private val authInterceptor: AuthInterceptor = mockk() // Mockando AuthInterceptor


    @Before
    fun setUp() {
        startKoin {
            modules(module {
                single { apiService }
                single { authInterceptor }
            })
        }

        // Injeção da dependência no repositório
        userRepository = UserRepositoryImpl(apiService, authInterceptor)
    }

    @Test
    fun post_registerUser_Success() = runBlocking {
        val registerRequest = getFakeRegisterRequest()
        val registerResponse = getFakeRegisterResponse()
        val response = Response.success(registerResponse)

        coEvery { apiService.registerUser(registerRequest) } returns response
        coEvery { authInterceptor.disableAuthHeader() } just Runs
        coEvery { authInterceptor.enableAuthHeader() } just Runs

        // Chama o método do repositório
        val result = userRepository.registerUser("John Doe", "john@example.com", "password", "password")

        // Verifica a emissão de resultado
        result.collect { result ->
            assertTrue(result.isSuccess)
            assertEquals(registerResponse, result.getOrNull())
        }

        // Verifica se os métodos do interceptor foram chamados
        coVerify { authInterceptor.disableAuthHeader() }
        coVerify { authInterceptor.enableAuthHeader() }
    }

    @Test
    fun post_loginUser_Success() = runBlocking {
        // Simulando a resposta do API com MockK
        val loginRequest = getFakeLoginRequest()
        val loginResponse = getFakeLoginResponse()
        val response = Response.success(loginResponse)

        coEvery { apiService.loginUser(loginRequest) } returns response
        coEvery { authInterceptor.disableAuthHeader() } just Runs
        coEvery { authInterceptor.enableAuthHeader() } just Runs

        // Chama o método do repositório
        val result = userRepository.login("John Doe", "john@example.com")

        // Verifica a emissão de resultado
        result.collect { result ->
            assertTrue(result.isSuccess)
            assertEquals(loginResponse, result.getOrNull())
        }

        // Verifica se os métodos do interceptor foram chamados
        coVerify { authInterceptor.disableAuthHeader() }
        coVerify { authInterceptor.enableAuthHeader() }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}