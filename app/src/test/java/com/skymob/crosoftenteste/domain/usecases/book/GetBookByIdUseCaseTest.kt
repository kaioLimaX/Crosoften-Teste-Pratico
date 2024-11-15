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

class GetBookByIdUseCaseTest {
    private lateinit var getBookByIdUseCase: GetBookByIdUseCase
    private val bookRepository: BookRepository = mockk()


    @Before
    fun setUp() {
        startKoin {
            modules(module {
                single { bookRepository }
            })
        }
        getBookByIdUseCase = GetBookByIdUseCase(bookRepository)

    }

    @Test
    fun getBookByIdUseCase_testGetBookById_success() = runBlocking {

        coEvery { bookRepository.getBookById(1) } returns flowOf(
            Result.success(
                getFakeData()
            )
        )


        val result = getBookByIdUseCase.invoke(1)
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

