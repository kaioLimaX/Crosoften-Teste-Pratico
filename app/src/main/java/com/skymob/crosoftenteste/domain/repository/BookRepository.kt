package com.skymob.crosoftenteste.domain.repository

import com.skymob.crosoftenteste.data.remote.dto.book.BookRequest
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun getBooks(
        search: String? = "null",
        categoryId: Int? = null,
        page: Int? = null,
        limit: Int? = null
    ): Flow<Result<List<Data>>>

    suspend fun getBookById(id: Int): Flow<Result<Data>>

    suspend fun addBook(bookRequest : BookRequest) : Flow<Result<Data>>


}