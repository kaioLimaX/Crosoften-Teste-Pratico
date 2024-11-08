package com.skymob.crosoftenteste.presentation.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.skymob.crosoftenteste.databinding.FragmentListBookBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment

class ListFragment : BaseFragment<FragmentListBookBinding, ListViewModel>()  {
    override val viewModel: ListViewModel by viewModels()


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListBookBinding = FragmentListBookBinding.inflate(inflater, container, false)
}