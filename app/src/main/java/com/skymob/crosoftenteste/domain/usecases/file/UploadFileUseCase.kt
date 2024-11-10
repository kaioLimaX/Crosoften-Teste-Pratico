package com.skymob.crosoftenteste.domain.usecases.file

import android.net.Uri
import com.skymob.crosoftenteste.domain.repository.FileRepository
import okhttp3.MultipartBody

class UploadFileUseCase(private val fileRepository: FileRepository) {
    suspend operator fun invoke(uri : Uri) = fileRepository.uploadImage(uri)
}