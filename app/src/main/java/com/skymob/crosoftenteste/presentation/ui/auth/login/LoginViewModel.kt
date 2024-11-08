package com.skymob.crosoftenteste.presentation.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skymob.crosoftenteste.data.remote.dto.user.LoginResponse
import com.skymob.crosoftenteste.domain.usecases.auth.LoginUseCase
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import com.skymob.crosoftenteste.util.SharedPreferencesManager
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> get() = _isLoggedIn

    private val _loginStatus = MutableLiveData<ViewState<LoginResponse>>()
    val loginStatus: LiveData<ViewState<LoginResponse>> get() = _loginStatus

    init {
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() {
        val token = sharedPreferencesManager.getToken()
        _isLoggedIn.value = !token.isNullOrEmpty() // Atualiza o LiveData com o estado de login
    }

    fun login(
        credential: String,
        password: String,
        keepLogin : Boolean
    ) {
        viewModelScope.launch {
            _loginStatus.value = ViewState.Loading()
            loginUseCase(credential, password).collect { result ->
                result.onSuccess {
                    _loginStatus.value = ViewState.Sucess(it)
                    if(keepLogin){
                        sharedPreferencesManager.saveToken(it.token)
                    }
                }.onFailure {
                    _loginStatus.value = ViewState.Error(it.message ?: "Erro desconhecido")
                }

            }

        }
    }
}