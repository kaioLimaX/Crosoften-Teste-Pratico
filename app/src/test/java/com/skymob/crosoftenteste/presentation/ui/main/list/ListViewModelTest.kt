package com.skymob.crosoftenteste.presentation.ui.main.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.data.remote.dto.user.LoginResponse
import com.skymob.crosoftenteste.data.remote.dto.user.RegisterResponse
import com.skymob.crosoftenteste.data.remote.models.getFakeBookResponse
import com.skymob.crosoftenteste.data.remote.models.getFakeData
import com.skymob.crosoftenteste.data.remote.models.getFakeRegisterResponse
import com.skymob.crosoftenteste.domain.usecases.auth.RegisterUseCase
import com.skymob.crosoftenteste.domain.usecases.book.DeleteBookUseCase
import com.skymob.crosoftenteste.domain.usecases.book.GetAllBooksUseCase
import com.skymob.crosoftenteste.domain.usecases.book.GetBookByIdUseCase
import com.skymob.crosoftenteste.presentation.ui.auth.register.RegisterViewModel
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import com.skymob.crosoftenteste.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import java.time.Instant
import kotlin.test.Test

class ListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()  // Para execução do LiveData

    private lateinit var listViewModel: ListViewModel
    private lateinit var getAllBooksUseCase: GetAllBooksUseCase
    private lateinit var getBookByIdUseCase: GetBookByIdUseCase
    private lateinit var deleteBookUseCase: DeleteBookUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        // Cria mocks usando MockK
        getAllBooksUseCase = mockk()
        getBookByIdUseCase = mockk()

        listViewModel = ListViewModel(getAllBooksUseCase, getBookByIdUseCase,deleteBookUseCase)
    }

    @Test
    fun testGetAllBooksUseCase_returnSuccess() = runBlocking {
        val bookResponse = getFakeBookResponse()
        coEvery {
            getAllBooksUseCase.invoke()
        } returns flowOf(Result.success(bookResponse.data!!))

        val observer: Observer<ViewState<List<Data>>> = mockk(relaxed = true)
        listViewModel.listBooksStatus.observeForever(observer)

        listViewModel.getBooks()

        verifyOrder {
            observer.onChanged(match { it is ViewState.Loading })
            observer.onChanged(match { it is ViewState.Sucess && it.data == bookResponse.data })
        }

    }

    @Test
    fun `test getBooks success`() = runTest {
        val bookResponse = getFakeBookResponse()
        coEvery { getAllBooksUseCase.invoke() } returns flowOf(Result.success(bookResponse.data!!))

        listViewModel.getBooks()

        // Verifica o estado inicial (Loading)
        val loadingState = listViewModel.listBooksStatus.getOrAwaitValue()
        assertTrue(loadingState is ViewState.Loading)

        // Avança o tempo para garantir que o Flow seja processado
        advanceUntilIdle()

        // Verifica o estado final (Sucess)
        val successState = listViewModel.listBooksStatus.getOrAwaitValue()
        assertTrue(successState is ViewState.Sucess)
        assertEquals(bookResponse.data, (successState as ViewState.Sucess).data)

    }



}