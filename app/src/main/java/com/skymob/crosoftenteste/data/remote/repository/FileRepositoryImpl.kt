package com.skymob.crosoftenteste.data.remote.repository

import android.content.Context
import android.net.Uri
import com.skymob.crosoftenteste.data.remote.api.ApiService
import com.skymob.crosoftenteste.data.remote.dto.file.UploadResponse
import com.skymob.crosoftenteste.domain.repository.FileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody

class FileRepositoryImpl(private val apiService: ApiService) : FileRepository {


    override suspend fun uploadImage(image: MultipartBody.Part): Flow<Result<UploadResponse>> =
        flow {
            val response = apiService.uploadFile(image)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.success(body))
                } else {
                    emit(Result.failure(Exception("Response body is null")))
                }
            }else{
                val errorMessage = response.errorBody()?.string() ?: "Erro desconhecido"
                emit(Result.failure(Exception(errorMessage)))
            }
        }.catch {
            emit(Result.failure(it))
        }
}