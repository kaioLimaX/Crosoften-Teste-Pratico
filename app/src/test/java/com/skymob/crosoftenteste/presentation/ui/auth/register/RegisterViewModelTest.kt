package com.skymob.crosoftenteste.presentation.ui.auth.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.skymob.crosoftenteste.data.remote.dto.user.LoginResponse
import com.skymob.crosoftenteste.data.remote.dto.user.RegisterResponse
import com.skymob.crosoftenteste.data.remote.models.getFakeRegisterResponse
import com.skymob.crosoftenteste.domain.usecases.auth.RegisterUseCase
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class RegisterViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Para execução do LiveData

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var registerUseCase: RegisterUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        // Cria mocks usando MockK
        registerUseCase = mockk()

        registerViewModel = RegisterViewModel(registerUseCase)
    }

    @Test
    fun testRegister_validCredentials_returnSucess() = runBlocking {
        val registerResponse = getFakeRegisterResponse()
        coEvery {
            registerUseCase.invoke(any(), any(), any(), any())
        } returns flow {
            emit(Result.success(registerResponse))
        }

        val observer: Observer<ViewState<RegisterResponse>> = mockk(relaxed = true)
        registerViewModel.registerStatus.observeForever(observer)

        registerViewModel.registerUser("jhon", "jhon@example.com", "123456", "123456")

        verifyOrder {
            observer.onChanged(match{ it is ViewState.Loading })
            observer.onChanged(match{ it is ViewState.Sucess && it.data == registerResponse })
        }

    }


}