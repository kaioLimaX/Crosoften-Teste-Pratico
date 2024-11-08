package com.skymob.crosoftenteste.presentation.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymob.crosoftenteste.data.remote.dto.user.LoginResponse
import com.skymob.crosoftenteste.data.remote.dto.user.RegisterResponse
import com.skymob.crosoftenteste.domain.usecases.auth.RegisterUseCase
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    private val _registerStatus = MutableLiveData<ViewState<RegisterResponse>>()
    val registerStatus: LiveData<ViewState<RegisterResponse>> get() = _registerStatus

    fun registerUser(name: String, email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            _registerStatus.value = ViewState.Loading()
            registerUseCase(name, email, password, confirmPassword)
                .collect { result -> // Coleta o resultado do fluxo tratado
                    result.onSuccess {
                        _registerStatus.value = ViewState.Sucess(it)
                    }.onFailure {
                        _registerStatus.value = ViewState.Error(it.message ?: "Erro desconhecido")
                    }
                }
        }
    }
}