package com.skymob.crosoftenteste.data.remote.models

import com.skymob.crosoftenteste.data.remote.dto.book.BookRequest
import com.skymob.crosoftenteste.data.remote.dto.book.BookResponse
import com.skymob.crosoftenteste.data.remote.dto.book.Category
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.data.remote.dto.file.UploadResponse
import com.skymob.crosoftenteste.data.remote.dto.user.LoginRequest
import com.skymob.crosoftenteste.data.remote.dto.user.LoginResponse
import com.skymob.crosoftenteste.data.remote.dto.user.RegisterRequest
import com.skymob.crosoftenteste.data.remote.dto.user.RegisterResponse


fun getFakeRegisterRequest() : RegisterRequest = RegisterRequest("John Doe", "john@example.com", "password", "password")

fun getFakeRegisterResponse() : RegisterResponse = RegisterResponse(123, "John Doe", "john@example.com","10/10/2024", "10/10/2024")

fun getFakeLoginRequest() : LoginRequest = LoginRequest("John Doe", "john@example.com")

fun getFakeLoginResponse() : LoginResponse = LoginResponse("123")

fun getFakeUploadResponse() : UploadResponse = UploadResponse("http://www.teste.com")

fun getFakeBookRequest() : BookRequest = BookRequest( "", 1, "", "", "",)

fun getFakeBookResponse() : BookResponse = BookResponse(
    listOf(
        Data(1,"","","","","","")
    ),1,1,1,1

)

fun getFakeData() : Data = Data(1,"","","","","","")