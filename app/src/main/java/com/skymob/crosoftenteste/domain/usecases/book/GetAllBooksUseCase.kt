package com.skymob.crosoftenteste.domain.usecases.book

import com.skymob.crosoftenteste.domain.repository.BookRepository

class GetAllBooksUseCase(private val bookRepository: BookRepository) {
    suspend operator fun invoke() = bookRepository.getBooks()
}