package com.skymob.crosoftenteste.presentation.ui.main.profile

import androidx.lifecycle.ViewModel
import com.skymob.crosoftenteste.util.SharedPreferencesManager

class ProfileViewModel(
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    fun logout() {
        sharedPreferencesManager.clearSession()
    }


}