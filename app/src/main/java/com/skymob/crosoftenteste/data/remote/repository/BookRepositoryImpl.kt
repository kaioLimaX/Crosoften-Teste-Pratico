package com.skymob.crosoftenteste.data.remote.repository

import android.util.Log
import com.skymob.crosoftenteste.data.remote.api.ApiService
import com.skymob.crosoftenteste.data.remote.dto.book.BookRequest
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class BookRepositoryImpl(private val apiService: ApiService) : BookRepository {

    override suspend fun getBooks(
        search: String?,
        categoryId: Int?,
        page: Int?,
        limit: Int?
    ): Flow<Result<List<Data>>> = flow {
        val response = apiService.getBooks(search, categoryId, page, limit)

        if (response.isSuccessful) {
            val body = response.body()
            val dataList = body?.data?.filterNotNull() // Remove elementos nulos

            if (dataList != null) {
                Log.i("info_lista", "getBooks: $dataList ")
                emit(Result.success(dataList))

            } else {
                emit(Result.failure(Exception("Erro: Resposta vazia")))
            }
        } else {
            val errorMessage = response.errorBody()?.string() ?: "Erro desconhecido"
            emit(Result.failure(Exception(errorMessage)))
        }
    }.catch { e ->
        emit(Result.failure(e))
    }

    override suspend fun getBookById(id: Int): Flow<Result<Data>> = flow {
        val response = apiService.getBookById(id)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                emit(Result.success(body))
            } else {
                emit(Result.failure(Exception("Response body is null")))
            }
        } else {
            val errorMessage = response.errorBody()?.string() ?: "Erro desconhecido"
            emit(Result.failure(Exception(errorMessage)))
        }
    }.catch { e ->
        emit(Result.failure(e))
    }

    override suspend fun addBook(bookRequest: BookRequest): Flow<Result<Data>> = flow {
        val response = apiService.addBook(bookRequest)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                emit(Result.success(body))
            } else {
                emit(Result.failure(Exception("Response body is null")))
            }
        } else {
            val errorMessage = response.errorBody()?.string() ?: "Erro desconhecido"
            emit(Result.failure(Exception(errorMessage)))
        }

    }.catch { e ->
        emit(Result.failure(e))

    }
}