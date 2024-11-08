package com.skymob.crosoftenteste.data.remote.dto.book

data class BookRequest(
    val author: String? = null,
    val categoryId: Int? = null,
    val imageUrl: String? = null,
    val summary: String? = null,
    val title: String? = null
)