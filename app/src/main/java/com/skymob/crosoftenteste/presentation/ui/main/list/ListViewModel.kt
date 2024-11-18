package com.skymob.crosoftenteste.presentation.ui.main.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.domain.usecases.book.DeleteBookUseCase
import com.skymob.crosoftenteste.domain.usecases.book.GetAllBooksUseCase
import com.skymob.crosoftenteste.domain.usecases.book.GetBookByIdUseCase
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val getBookByIdUseCase: GetBookByIdUseCase,
    private val deleteBookUseCase: DeleteBookUseCase

) : ViewModel() {
    private val _listBooksStatus = MutableLiveData<ViewState<List<Data>>>()
    val listBooksStatus: LiveData<ViewState<List<Data>>> get() = _listBooksStatus

    private val _getBookDetailsStatus = MutableLiveData<ViewState<Data>?>()
    val getBookDetailsStatus: LiveData<ViewState<Data>?> get() = _getBookDetailsStatus

    private val _deleteBookStatus = MutableLiveData<ViewState<Unit>?>()
    val deleteBookStatus: LiveData<ViewState<Unit>?> get() = _deleteBookStatus


    fun getBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            _listBooksStatus.postValue(ViewState.Loading())
            getAllBooksUseCase()
                .collect { result ->
                    result.onSuccess {
                        _listBooksStatus.postValue(ViewState.Sucess(it))
                    }
                    result.onFailure {
                        _listBooksStatus.postValue(ViewState.Error(it.message.toString()))

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
                        _getBookDetailsStatus.value = null
                    }.onFailure {
                        _getBookDetailsStatus.value = ViewState.Error(it.message.toString())
                        _getBookDetailsStatus.value = null
                    }

                }


        }
    }

    fun deleteBook(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _deleteBookStatus.postValue(ViewState.Loading())
            deleteBookUseCase.invoke(id)
                .collect { result ->
                    result.onSuccess {
                        _deleteBookStatus.postValue(ViewState.Sucess(it))

                    }.onFailure {
                        _deleteBookStatus.postValue(ViewState.Error(it.message.toString()))
                    }

                }

        }


    }

    fun resetBookDetailsStatus() {
        _getBookDetailsStatus.value = null  // Ou um valor inicial apropriado
    }
    fun resetDeleteStatus() {
        _deleteBookStatus.value = null // Ou um valor inicial apropriado
    }
}
