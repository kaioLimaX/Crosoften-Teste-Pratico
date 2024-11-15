package com.skymob.crosoftenteste.data.remote.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import com.skymob.crosoftenteste.data.remote.api.ApiService
import com.skymob.crosoftenteste.data.remote.dto.file.UploadResponse
import com.skymob.crosoftenteste.util.prepareImageFile
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import net.bytebuddy.matcher.ElementMatchers.any
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import retrofit2.Response
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import kotlin.test.Test
import kotlin.test.fail


class FileRepositoryImplTest {

    private lateinit var context: Context
    private lateinit var fileRepository: FileRepositoryImpl
    private val apiService: ApiService = mockk() // Mockando ApiService

    @Before
    fun setUp() {
        // Inicializando o Koin com o módulo mockado
        startKoin {
            modules(module {
                single { apiService }
            })
        }
        context = mockk()


        // Injeção da dependência no repositório
        fileRepository = FileRepositoryImpl(apiService, context)
    }

    @Test
    fun post_addFile_success() = runBlocking {
        // Mock do URI e InputStream
        val mockUri = mockk<Uri>()
        val mockInputStream = ByteArrayInputStream(byteArrayOf(1, 2, 3))
        every { context.contentResolver.openInputStream(mockUri) } returns mockInputStream

        val tempDir = createTempDir()  // Cria um diretório temporário para o teste
        every { context.filesDir } returns tempDir

        // Mock da resposta do serviço de API
        val mockUploadResponse = UploadResponse("http://teste.com") // substitua com o valor correto
        coEvery { apiService.uploadFile(any()) } returns Response.success(mockUploadResponse)

        // Executa o teste
        val result = fileRepository.uploadImage(mockUri)

        result.collect {
            assertTrue(it.isSuccess)
        }


    }


    @After
    fun tearDown() {
        stopKoin()
    }
}