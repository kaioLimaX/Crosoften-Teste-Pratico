package com.skymob.crosoftenteste.data.remote.dto.book

data class BookResponse(
    val data: List<Data>?, // Lista de livros, pode ser nula
    val totalItems: Int,
    val totalPages: Int,
    val itemsPerPage: Int,
    val page: Int
)

data class Data(
    val id: Int,
    val title: String,
    val summary: String?,
    val author: String,
    val imageUrl: String,
    val createdAt: String,
    val updatedAt: String,
    val category: Category? = null
)

data class Category(
    val id: Int,
    val title: String,
    val createdAt: String,
    val updatedAt: String
)