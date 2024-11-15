package com.skymob.crosoftenteste.domain.usecases.book

import com.skymob.crosoftenteste.data.remote.models.getFakeData
import com.skymob.crosoftenteste.domain.repository.BookRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.Test

class GetAllBooksUseCaseTest {
    private lateinit var getAllBooksUseCase: GetAllBooksUseCase
    private val bookRepository: BookRepository = mockk()

    @Before
    fun setUp() {
        startKoin {
            modules(module {
                single { bookRepository }
            })
        }
        getAllBooksUseCase = GetAllBooksUseCase(bookRepository)

    }

    @Test
    fun getAllBooksUseCase_testGetAllBooks_success() = runBlocking {

        coEvery { bookRepository.getBooks() } returns flowOf(
            Result.success(
                listOf(getFakeData())
            )
        )

        val result = getAllBooksUseCase.invoke()
        result.collect {
            assert(it.isSuccess)
            assert(it.getOrNull() != null)
        }

    }

    @After
    fun tearDown() {
        stopKoin()
    }
}