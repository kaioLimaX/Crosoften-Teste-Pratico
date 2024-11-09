package com.skymob.crosoftenteste.presentation.ui.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.domain.usecases.book.SearchBookUseCase
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchViewModel(private val searchBookUseCase: SearchBookUseCase) : ViewModel() {

    private val _searchList = MutableLiveData<ViewState<List<Data>>>()
    val searchList: LiveData<ViewState<List<Data>>> get() = _searchList

    private val searchQuery = MutableStateFlow("")

    init {
        // Observe as mudanças no texto da busca com debounce
        configSearch()
    }


    @OptIn(FlowPreview::class)
    private fun configSearch() {
        searchQuery
            .debounce(300) // Aguarda 300ms após o último caractere digitado
            .distinctUntilChanged() // Evita pesquisas duplicadas
            .filter { it.isNotEmpty() } // Ignora buscas vazias
            .onEach { query ->
                searchBooks(query)
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) {
        viewModelScope.launch {
            delay(1000)
            _searchList.value = ViewState.Loading()
            searchBookUseCase.invoke(query).collect { result ->
                result.onSuccess {
                    _searchList.value = ViewState.Sucess(it)
                }.onFailure {
                    _searchList.value = ViewState.Error(it.message.toString())
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }
}