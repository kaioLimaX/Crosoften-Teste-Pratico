package com.skymob.crosoftenteste.domain.usecases.file

import android.net.Uri
import com.skymob.crosoftenteste.data.remote.models.getFakeUploadResponse
import com.skymob.crosoftenteste.domain.repository.FileRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class UploadFileUseCaseTest {
    private lateinit var uploadFileUseCase: UploadFileUseCase
    private val fileRepository: FileRepository = mockk()

    @Before
    fun setUp() {

        startKoin {
            modules(module {
                single { fileRepository }
            })
        }

        uploadFileUseCase = UploadFileUseCase(fileRepository)

    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun uploadFileUseCase_testSendFile_success() = runBlocking {
        val uri = Uri.parse("file:///android_asset/sample_image.png")

        coEvery { fileRepository.uploadImage(uri) } returns flowOf(
            Result.success(
                getFakeUploadResponse()
            )
        )

        val result = uploadFileUseCase(uri)
        result.collect {
            assert(it.isSuccess)
            assert(it.getOrNull() != null)
        }


    }
}