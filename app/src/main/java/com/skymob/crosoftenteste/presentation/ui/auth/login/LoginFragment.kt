package com.skymob.crosoftenteste.presentation.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.skymob.crosoftenteste.R
import com.skymob.crosoftenteste.databinding.FragmentLoginBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import com.skymob.crosoftenteste.presentation.ui.util.setupClickableTextView
import com.skymob.crosoftenteste.util.AlertLoading
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by viewModel()
    private val alertLoading by lazy {
        AlertLoading(requireContext())
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOnClick()
        initObserver()

    }

    private fun initObserver() {
        viewModel.isLoggedIn.observe(viewLifecycleOwner) { isLoggedIn ->
            if (isLoggedIn == true) {
                navigateToMenu()
            }
        }

        viewModel.loginStatus.observe(viewLifecycleOwner) { status ->
            when(status){
                is ViewState.Error -> {
                    alertLoading.close()
                    Toast.makeText(requireContext(), status.message, Toast.LENGTH_SHORT).show()
                }
                is ViewState.Loading -> {
                    alertLoading.show("Fazendo Login")
                }
                is ViewState.Sucess -> {
                    alertLoading.close()
                    navigateToMenu()
                }
            }
        }
    }

    private fun initOnClick() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edtCredencials.text.toString()
            val password = binding.edtPassword.text.toString()
            val keepLogin = binding.switchKeepLogin.isChecked
            viewModel.login(email,password, keepLogin)
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