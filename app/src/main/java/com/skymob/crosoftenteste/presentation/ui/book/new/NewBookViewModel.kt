package com.skymob.crosoftenteste.presentation.ui.book.new

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymob.crosoftenteste.data.remote.dto.book.BookRequest
import com.skymob.crosoftenteste.domain.usecases.book.AddBookUseCase
import com.skymob.crosoftenteste.domain.usecases.file.UploadFileUseCase
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import com.skymob.crosoftenteste.util.prepareImageFile
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class NewBookViewModel(
    private val uploadFileUseCase: UploadFileUseCase,
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {

    private val _state =
        MutableLiveData<ViewState<Unit>>()
    val state: LiveData<ViewState<Unit>> = _state

    fun uploadImageAndAddBook(uri: Uri, bookRequest: BookRequest) {

        _state.value = ViewState.Loading()

        viewModelScope.launch {
            uploadFileUseCase(uri).collect { result ->
                result.onSuccess {
                    val imageUrl = it.url
                    addBook(bookRequest.copy(imageUrl = imageUrl))
                }.onFailure {
                    _state.value = ViewState.Error(it.message ?: "Erro desconhecido")
                }

            }
        }
    }

    private suspend fun addBook(bookRequest: BookRequest) {
        addBookUseCase(bookRequest).collect { result ->
            result.onSuccess {
                _state.value =
                    ViewState.Sucess(Unit)
            }.onFailure {
                _state.value = ViewState.Error(it.message ?: "Erro desconhecido")
            }

        }

    }
}

