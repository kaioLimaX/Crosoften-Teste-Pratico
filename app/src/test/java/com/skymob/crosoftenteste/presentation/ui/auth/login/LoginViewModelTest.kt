package com.skymob.crosoftenteste.presentation.ui.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.skymob.crosoftenteste.data.remote.dto.user.LoginResponse
import com.skymob.crosoftenteste.domain.usecases.auth.LoginUseCase
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import com.skymob.crosoftenteste.util.SharedPreferencesManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.bouncycastle.util.test.SimpleTest.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Para execução do LiveData

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private val testDispatcher = TestCoroutineDispatcher()  // Criando o TestCoroutineDispatcher

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        // Configura o Dispatchers.Main para o TestCoroutineDispatcher
        Dispatchers.setMain(testDispatcher)

        // Cria mocks usando MockK
        loginUseCase = mockk()
        sharedPreferencesManager = mockk()

        every { sharedPreferencesManager.saveToken(any()) } returns Unit
        every { sharedPreferencesManager.checkKeepLogin() } returns true
        every { sharedPreferencesManager.keepLogin() } returns Unit

        // Inicializa o ViewModel com os mocks
        loginViewModel = LoginViewModel(loginUseCase, sharedPreferencesManager)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        // Limpeza do dispatcher após o teste
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testLogin_credencials_returnSucess() = runTest {
        // Simula a resposta de sucesso do LoginUseCase
        val loginResponse = LoginResponse(token = "dummy_token")
        coEvery { loginUseCase.invoke(any(), any()) } returns flow {
            emit(Result.success(loginResponse))
        }

        // Cria o observer do LiveData
        val observer: Observer<ViewState<LoginResponse>> = mockk(relaxed = true)
        loginViewModel.loginStatus.observeForever(observer)

        // Chama a função de login
        loginViewModel.login("test@example.com", "password", keepLogin = true)

        // Verifica se o estado foi atualizado para Loading e depois Sucesso
        verifyOrder {
            observer.onChanged(match { it is ViewState.Loading })
            observer.onChanged(match { it is ViewState.Sucess && it.data == loginResponse })
        }

        // Verifica se o token foi salvo e a flag keepLogin foi chamada
        coVerify { sharedPreferencesManager.saveToken("dummy_token") }
        coVerify { sharedPreferencesManager.keepLogin() }
    }
}