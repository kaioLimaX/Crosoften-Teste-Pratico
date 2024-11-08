package com.skymob.crosoftenteste.domain.usecases.file

import com.skymob.crosoftenteste.domain.repository.FileRepository
import okhttp3.MultipartBody

class UploadFileUseCase(private val fileRepository: FileRepository) {
    suspend operator fun invoke(file: MultipartBody.Part) = fileRepository.uploadImage(file)
}