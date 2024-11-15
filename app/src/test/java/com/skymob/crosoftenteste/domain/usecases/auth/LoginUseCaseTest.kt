package com.skymob.crosoftenteste.domain.usecases.auth

import com.skymob.crosoftenteste.data.remote.dto.user.LoginResponse
import com.skymob.crosoftenteste.data.remote.models.getFakeLoginResponse
import com.skymob.crosoftenteste.domain.repository.BookRepository
import com.skymob.crosoftenteste.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.Test

class LoginUseCaseTest {
    private lateinit var loginUseCase: LoginUseCase
    private val authRepository: UserRepository = mockk()

    @Before
    fun setUp() {
        startKoin {
            modules(module {
                single { authRepository }
            })
        }
        loginUseCase = LoginUseCase(authRepository)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
     fun loginUseCase_testValidCredentials_success() = runBlocking {
         val credential = "john.mclean@examplepetstore.com"
        val password = "password123"

        coEvery { authRepository.login(credential, password) } returns flowOf(Result.success(
            getFakeLoginResponse()
        ))

        val result = loginUseCase(credential, password)

        result.collect {
            assert(it.isSuccess)
            assert(it.getOrNull() != null)

        }

    }
}