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

class SearchBookUseCaseTest {

    private lateinit var searchBookUseCase: SearchBookUseCase
    private val bookRepository: BookRepository = mockk()


    @Before
    fun setUp() {
        startKoin {
            modules(module {
                single { bookRepository }
            })
        }
        searchBookUseCase = SearchBookUseCase(bookRepository)

    }

    @Test
    fun searchBookUseCase_testSearchBook_success() = runBlocking {

        coEvery { bookRepository.getBooks(search = "") } returns flowOf(
            Result.success(
                listOf(getFakeData())
            )
        )

        val result = searchBookUseCase.invoke("")
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