package com.skymob.crosoftenteste.presentation.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.skymob.crosoftenteste.databinding.FragmentRegisterBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import com.skymob.crosoftenteste.util.AlertLoading
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>() {
    override val viewModel: RegisterViewModel by viewModel()

    private val alertLoading by lazy {
        AlertLoading(requireContext())
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding  = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOnClick()
        initObserver()
    }

    private fun initOnClick() {
        with(binding){
            btnRegister.setOnClickListener {
                val name = edtNome.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                val confirmPassword = edtConfirmacaoPassword.text.toString()
                viewModel.registerUser(name, email, password, confirmPassword)
            }
        }
    }

    private fun initObserver() {
        viewModel.registerStatus.observe(viewLifecycleOwner){status->
            when(status){
                is ViewState.Error -> {
                    alertLoading.close()
                    Toast.makeText(requireContext(), "erro ao registrar usuario", Toast.LENGTH_SHORT).show()
                }
                is ViewState.Loading -> {
                    alertLoading.show("Registrando Usuario")
                }
                is ViewState.Sucess -> {
                    alertLoading.close()
                    Toast.makeText(requireContext(), "Sucesso ao cadastrar", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}