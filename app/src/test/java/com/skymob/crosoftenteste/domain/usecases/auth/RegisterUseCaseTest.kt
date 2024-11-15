package com.skymob.crosoftenteste.domain.usecases.auth

import com.skymob.crosoftenteste.data.remote.models.getFakeRegisterResponse
import com.skymob.crosoftenteste.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.Test


class RegisterUseCaseTest {
    private lateinit var registerUseCase: RegisterUseCase
    private val authRepository: UserRepository = mockk()

    @Before
    fun setUp() {
        startKoin {
            modules(module {
                single { authRepository }
            })
        }
        registerUseCase = RegisterUseCase(authRepository)

    }

    @After
    fun tearDown() {
        stopKoin()

    }

    @Test
    fun registerUseCase_testValidCredentials_success() = runBlocking {
        val name = "John Doe"
        val email = "william.henry.harrison@example-pet-store.com"
        val password = "password123"
        val confirmPassword = "password123"

        coEvery {
            authRepository.registerUser(
                name,
                email,
                password,
                confirmPassword
            )
        } returns flowOf(Result.success(getFakeRegisterResponse()))


        val result = registerUseCase(name, email, password, confirmPassword)

        result.collect {
            assert(it.isSuccess)
            assert(it.getOrNull() != null)

        }
    }
}