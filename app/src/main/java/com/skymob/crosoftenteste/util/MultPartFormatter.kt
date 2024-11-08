package com.skymob.crosoftenteste.util

import android.net.Uri
import android.content.Context
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


fun prepareImageFile(context: Context, uri: Uri): MultipartBody.Part {
    val filesDir = context.filesDir
    val file = File(filesDir, "image.png")

    // Abre o InputStream da URI e copia para o arquivo local
    context.contentResolver.openInputStream(uri)?.use { inputStream ->
        FileOutputStream(file).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }
    // Cria o RequestBody e o MultipartBody.Part
    val requestBody = file.asRequestBody("image/png".toMediaType())
    return MultipartBody.Part.createFormData("file", file.name, requestBody)
}