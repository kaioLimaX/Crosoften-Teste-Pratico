package com.skymob.crosoftenteste.domain.usecases.book

import com.skymob.crosoftenteste.data.remote.models.getFakeBookRequest
import com.skymob.crosoftenteste.data.remote.models.getFakeData
import com.skymob.crosoftenteste.domain.repository.BookRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.Test

class AddBookUseCaseTest {
    private lateinit var addBookUseCase: AddBookUseCase
    private val bookRepository: BookRepository = mockk()

    @Before
    fun setUp() {
        startKoin {
            modules(module {
                single { bookRepository }
            })
        }
        addBookUseCase = AddBookUseCase(bookRepository)

    }

    @After
    fun tearDown() {
        stopKoin()

    }

    @Test
    fun addBookUseCase_validBook_Success() = runBlocking {

        coEvery { bookRepository.addBook(getFakeBookRequest()) } returns flowOf(
            Result.success(
                getFakeData()
            )
        )

        val result = addBookUseCase.invoke((getFakeBookRequest()))

        result.collect {
            assert(it.isSuccess)
            assert(it.getOrNull() != null)
        }

    }
}