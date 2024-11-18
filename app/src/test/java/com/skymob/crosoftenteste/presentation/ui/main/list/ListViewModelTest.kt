package com.skymob.crosoftenteste.presentation.ui.main.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.data.remote.models.getFakeBookResponse
import com.skymob.crosoftenteste.domain.usecases.book.DeleteBookUseCase
import com.skymob.crosoftenteste.domain.usecases.book.GetAllBooksUseCase
import com.skymob.crosoftenteste.domain.usecases.book.GetBookByIdUseCase
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var listViewModel: ListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        val testModule = module {
            single<GetAllBooksUseCase> { mockk() }
            single<GetBookByIdUseCase> { mockk() }
            single<DeleteBookUseCase> { mockk() }
        }

        startKoin { modules(testModule) }

        val getAllBooksUseCase: GetAllBooksUseCase = get()
        val getBookByIdUseCase: GetBookByIdUseCase = get()
        val deleteBookUseCase: DeleteBookUseCase = get()

        listViewModel = ListViewModel(getAllBooksUseCase, getBookByIdUseCase, deleteBookUseCase)
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `test getBooks success`() = runTest {
        val bookResponse = getFakeBookResponse()
        val getAllBooksUseCase: GetAllBooksUseCase = get()

        coEvery { getAllBooksUseCase.invoke() } returns flowOf(Result.success(bookResponse.data!!))

        val observer: Observer<ViewState<List<Data>>> = mockk(relaxed = true)
        listViewModel.listBooksStatus.observeForever(observer)

        listViewModel.getBooks()
        advanceUntilIdle()

        verifyOrder {
            observer.onChanged(match { it is ViewState.Loading })
            observer.onChanged(match { it is ViewState.Sucess && it.data == bookResponse.data })
        }

        listViewModel.listBooksStatus.removeObserver(observer)
    }


    @Test
    fun `test getBookDetails success`() = runTest {
        val bookDetail = getFakeBookResponse().data!!.first()
        val getBookByIdUseCase: GetBookByIdUseCase = get()

        coEvery { getBookByIdUseCase.invoke(bookDetail.id) } returns flowOf(
            Result.success(
                bookDetail
            )
        )

        val observer: Observer<ViewState<Data>?> = mockk(relaxed = true)
        listViewModel.getBookDetailsStatus.observeForever(observer)

        listViewModel.getBookDetails(bookDetail.id)
        advanceUntilIdle()

        verifyOrder {
            observer.onChanged(match { it is ViewState.Loading })
            observer.onChanged(match { it is ViewState.Sucess && it.data == bookDetail })
            observer.onChanged(null) // Deve limpar o estado
        }

        listViewModel.getBookDetailsStatus.removeObserver(observer)
    }

    @Test
    fun `test deleteBook success`() = runTest {
        val bookId = 1
        val deleteBookUseCase: DeleteBookUseCase = get()

        coEvery { deleteBookUseCase.invoke(bookId) } returns flowOf(Result.success(Unit))

        val observer: Observer<ViewState<Unit>?> = mockk(relaxed = true)
        listViewModel.deleteBookStatus.observeForever(observer)

        listViewModel.deleteBook(bookId)
        advanceUntilIdle()

        verifyOrder {
            observer.onChanged(match { it is ViewState.Loading })
            observer.onChanged(match { it is ViewState.Sucess })
        }

        listViewModel.deleteBookStatus.removeObserver(observer)
    }
}