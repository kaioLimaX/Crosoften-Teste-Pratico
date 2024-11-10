package com.skymob.crosoftenteste.presentation.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.skymob.crosoftenteste.R
import com.skymob.crosoftenteste.databinding.FragmentProfileBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModel()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnClick()
    }

    private fun initOnClick() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(
                R.id.action_profileFragment_to_loginFragment, // Ação para ir de volta ao login
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.profileFragment, true) // Limpa a pilha até o mainFragment
                    .build()
            )
        }
    }
}