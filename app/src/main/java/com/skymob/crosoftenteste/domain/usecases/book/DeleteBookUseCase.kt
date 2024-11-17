package com.skymob.crosoftenteste.domain.usecases.book

import com.skymob.crosoftenteste.domain.repository.BookRepository

class DeleteBookUseCase(private val bookRepository: BookRepository) {
    suspend operator fun invoke(id: Int) = bookRepository.removeBook(id)
    }

