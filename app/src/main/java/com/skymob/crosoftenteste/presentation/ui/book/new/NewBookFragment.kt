package com.skymob.crosoftenteste.presentation.ui.book.new

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.skymob.crosoftenteste.databinding.FragmentNewBookBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment

class NewBookFragment : BaseFragment<FragmentNewBookBinding, NewBookViewModel>()  {
    override val viewModel: NewBookViewModel by viewModels()


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewBookBinding = FragmentNewBookBinding.inflate(inflater, container, false)
}