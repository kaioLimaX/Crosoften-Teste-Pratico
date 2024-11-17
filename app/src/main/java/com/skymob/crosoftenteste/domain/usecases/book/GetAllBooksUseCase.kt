package com.skymob.crosoftenteste.domain.usecases.book

import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class GetAllBooksUseCase(private val bookRepository: BookRepository) {
    suspend operator fun invoke() : Flow<Result<List<Data>>> {
        return bookRepository.getBooks()
    }
}