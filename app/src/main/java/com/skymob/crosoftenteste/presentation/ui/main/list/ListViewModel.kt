package com.skymob.crosoftenteste.presentation.ui.main.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.domain.usecases.book.GetAllBooksUseCase
import com.skymob.crosoftenteste.domain.usecases.book.GetBookByIdUseCase
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import kotlinx.coroutines.launch

class ListViewModel(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val getBookByIdUseCase: GetBookByIdUseCase

) : ViewModel() {
    private val _listBooksStatus = MutableLiveData<ViewState<List<Data>>>()
    val listBooksStatus: LiveData<ViewState<List<Data>>> get() = _listBooksStatus

    private val _getBookDetailsStatus = MutableLiveData<ViewState<Data>?>()
    val getBookDetailsStatus: LiveData<ViewState<Data>?> get() = _getBookDetailsStatus

    init {
        getBooks()
    }

    private fun getBooks() {
        viewModelScope.launch {
            _listBooksStatus.value = ViewState.Loading()
            getAllBooksUseCase.invoke()
                .collect { result ->
                    result.onSuccess {
                        _listBooksStatus.value = ViewState.Sucess(it)
                    }
                    result.onFailure {
                        _listBooksStatus.value = ViewState.Error(it.message.toString())

                    }
                }
        }

    }

    fun getBookDetails(id: Int) {
        viewModelScope.launch {
            _getBookDetailsStatus.value = ViewState.Loading()
            getBookByIdUseCase.invoke(id)
                .collect { result ->
                    result.onSuccess {
                        _getBookDetailsStatus.value = ViewState.Sucess(it)
                    }.onFailure {
                        _getBookDetailsStatus.value = ViewState.Error(it.message.toString())
                    }

                }


        }
    }

    fun resetBookDetailsStatus() {
        _getBookDetailsStatus.value = null  // Ou um valor inicial apropriado
    }

}