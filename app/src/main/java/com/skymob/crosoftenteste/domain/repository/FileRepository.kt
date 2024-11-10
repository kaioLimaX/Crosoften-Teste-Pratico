package com.skymob.crosoftenteste.domain.repository

import android.content.Context
import android.net.Uri
import com.skymob.crosoftenteste.data.remote.dto.file.UploadResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface FileRepository {
    suspend fun uploadImage(image : Uri): Flow<Result<UploadResponse>>
}