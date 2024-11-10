package com.skymob.crosoftenteste.data.remote.dto.book

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class BookResponse(
    val data: List<Data>?, // Lista de livros, pode ser nula
    val totalItems: Int,
    val totalPages: Int,
    val itemsPerPage: Int,
    val page: Int
)

@Parcelize
data class Data(
    val id: Int,
    val title: String,
    val summary: String?,
    val author: String,
    val imageUrl: String,
    val createdAt: String,
    val updatedAt: String,
    val category: Category? = null
) : Parcelable

@Parcelize
data class Category(
    val id: Int,
    val title: String,
    val createdAt: String,
    val updatedAt: String
) : Parcelable