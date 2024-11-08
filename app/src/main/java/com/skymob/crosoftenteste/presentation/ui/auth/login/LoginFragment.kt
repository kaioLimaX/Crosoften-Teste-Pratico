package com.skymob.crosoftenteste.presentation.ui.auth.login

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.skymob.crosoftenteste.R
import com.skymob.crosoftenteste.databinding.FragmentLoginBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment
import com.skymob.crosoftenteste.presentation.ui.util.setupClickableTextView

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by viewModels()


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            navigateToMenu()
        }
        formatTextButtonRegister()

    }

    private fun formatTextButtonRegister() {
        val textRegister = binding.txtRegister.text.toString()
        setupClickableTextView(
            binding.txtRegister,
            textRegister,
            "Registre-se",
            onClick = {
                navigateToRegister()
            }
        )
    }

    private fun navigateToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun navigateToMenu() {
        findNavController().navigate(
            R.id.action_loginFragment_to_mainFragment,
            null,
            NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true)
                .build()
        )
    }
}