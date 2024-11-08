package com.skymob.crosoftenteste.domain.usecases.book

import com.skymob.crosoftenteste.domain.repository.BookRepository

class GetBookByIdUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(id: Int) = repository.getBookById(id)
}