package com.skymob.crosoftenteste.presentation.ui.auth.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.skymob.crosoftenteste.databinding.FragmentRegisterBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {
    override val viewModel: RegisterViewModel by viewModels()


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding  = FragmentRegisterBinding.inflate(inflater, container, false)
}