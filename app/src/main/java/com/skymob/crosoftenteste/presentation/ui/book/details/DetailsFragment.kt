package com.skymob.crosoftenteste.presentation.ui.book.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.skymob.crosoftenteste.databinding.FragmentDetailsBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment

class DetailsBookFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {
    override val viewModel: DetailsViewModel by viewModels()


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
}