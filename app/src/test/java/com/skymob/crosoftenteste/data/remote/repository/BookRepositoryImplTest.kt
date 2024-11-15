package com.skymob.crosoftenteste.data.remote.repository

import com.skymob.crosoftenteste.data.remote.api.ApiService
import com.skymob.crosoftenteste.data.remote.api.interceptors.AuthInterceptor
import com.skymob.crosoftenteste.data.remote.dto.book.BookRequest
import com.skymob.crosoftenteste.data.remote.dto.book.BookResponse
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.data.remote.dto.user.RegisterRequest
import com.skymob.crosoftenteste.data.remote.dto.user.RegisterResponse
import com.skymob.crosoftenteste.domain.repository.BookRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import retrofit2.Response
import kotlin.test.Test

class BookRepositoryImplTest {
    private lateinit var bookRepository: BookRepositoryImpl
    private val apiService: ApiService = mockk() // Mockando ApiService


    @Before
    fun setUp() {
        startKoin {
            modules(module {
                single { apiService }
            })
        }

        // Injeção da dependência no repositório
        bookRepository = BookRepositoryImpl(apiService)
    }

    @Test
    fun get_getListBooks_Success() = runBlocking {
        val data = listOf(
            Data(1, "Title", "Summary", "Author", "imageUrl", "createdAt", "updatedAt"),
            Data(2, "Title2", "Summary2", "Author2", "imageUrl2", "createdAt2", "updatedAt2"),
            Data(3, "Title3", "Summary3", "Author3", "imageUrl3", "createdAt3", "updatedAt3")
        )
        val bookResponse = BookResponse(data, 31, 1, 3, 1)
        val response = Response.success(bookResponse)

        // Mock da chamada do API Service para retornar o Response correto
        coEvery { apiService.getBooks() } returns response

        // Chama o método do repositório
        val resultFlow =
            bookRepository.getBooks()

        // Coleta o resultado do Flow e realiza as asserções
        resultFlow.collect { result ->
            assertTrue(result.isSuccess)
            assertEquals(bookResponse.data, result.getOrNull())


        }
    }

    @Test
    fun get_getBookById_Success() = runBlocking {
        val data = Data(1, "Title", "Summary", "Author", "imageUrl", "createdAt", "updatedAt")
        val response = Response.success(data)

        // Mock da chamada do API Service para retornar o Response correto
        coEvery { apiService.getBookById(1) } returns response

        // Chama o método do repositório
        val resultFlow = bookRepository.getBookById(1)

        // Coleta o resultado do Flow e realiza as asserções
        resultFlow.collect { result ->
            assertTrue(result.isSuccess)
            assertEquals(data, result.getOrNull())


        }
    }

    @Test
    fun post_addBook_Success() = runBlocking {
       val bookRequest = BookRequest( "Title", 1, "imageUrl", "Summary")
        val bookResponse = Data(1, "Title", "Summary", "Author", "imageUrl", "createdAt", "updatedAt")
        val response  = Response.success(bookResponse)

        // Mock da chamada do API Service para retornar o Response correto
        coEvery { apiService.addBook(bookRequest) } returns response

        // Chama o método do repositório
        val resultFlow = bookRepository.addBook(bookRequest)

        // Coleta o resultado do Flow e realiza as asserções
        resultFlow.collect { result ->
            assertTrue(result.isSuccess)
            assertEquals(bookResponse, result.getOrNull())


        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}