package com.skymob.crosoftenteste.data.remote.api

import com.skymob.crosoftenteste.data.remote.dto.book.BookRequest
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.data.remote.dto.book.BookResponse
import com.skymob.crosoftenteste.data.remote.dto.file.UploadResponse
import com.skymob.crosoftenteste.data.remote.dto.user.LoginRequest
import com.skymob.crosoftenteste.data.remote.dto.user.LoginResponse
import com.skymob.crosoftenteste.data.remote.dto.user.RegisterRequest
import com.skymob.crosoftenteste.data.remote.dto.user.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("users/")
    suspend fun registerUser(@Body user: RegisterRequest) : Response<RegisterResponse>

    @POST("auth/login")
    suspend fun loginUser(@Body credential: LoginRequest) : Response<LoginResponse>

    @Multipart
    @POST("/upload-file")
    suspend fun uploadFile(
        @Part file: MultipartBody.Part,
    ): Response<UploadResponse>

    @POST("books")
    suspend fun addBook(@Body request: BookRequest): Response<Data>

    @GET("books")
    suspend fun getBooks(
        @Query("search") search: String? = null,
        @Query("categoryId") categoryId: Int? = null,
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
    ): Response<BookResponse>

    @GET("books/{id}")
    suspend fun getBookById(@Path("id") id: Int): Response<Data>
}
