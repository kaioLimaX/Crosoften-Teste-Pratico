package com.skymob.crosoftenteste.domain.usecases.book

import com.skymob.crosoftenteste.data.remote.models.getFakeData
import com.skymob.crosoftenteste.domain.repository.BookRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class DeleteBookUseCaseTest {
    private lateinit var deleteBookUseCase: DeleteBookUseCase
    private lateinit var bookRepository: BookRepository

    @Before
    fun setUp() {
        startKoin {
            modules(module {
                single { bookRepository }
            })


        }
        bookRepository = mockk()
        deleteBookUseCase = DeleteBookUseCase(bookRepository)


    }

    @Test
    fun deleteBookUseCase_returnSuccess() = runTest {
        coEvery { bookRepository.removeBook(1) } returns flowOf(
            Result.success(
                Unit
            )
        )
        val result = deleteBookUseCase.invoke(1)
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