package com.skymob.crosoftenteste.domain.usecases.book

import com.skymob.crosoftenteste.data.remote.dto.book.BookRequest
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.domain.repository.BookRepository

class AddBookUseCase(private val bookRepository: BookRepository) {
    suspend operator fun invoke(bookRequest: BookRequest) = bookRepository.addBook(bookRequest)
}