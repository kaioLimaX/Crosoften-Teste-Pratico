package com.skymob.crosoftenteste.domain.usecases.book

import com.skymob.crosoftenteste.domain.repository.BookRepository

class SearchBookUseCase(private val bookRepository: BookRepository) {
    suspend operator fun invoke(query: String) = bookRepository.getBooks(search = query)

}