package com.skymob.crosoftenteste.presentation.ui.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.skymob.crosoftenteste.databinding.FragmentSearchBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override val viewModel: SearchViewModel by viewModels()


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding  = FragmentSearchBinding.inflate(inflater, container, false)
}