package com.skymob.crosoftenteste.presentation.ui.book.details

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.skymob.crosoftenteste.R
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.databinding.FragmentDetailsBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment
import com.skymob.crosoftenteste.presentation.ui.util.formatDate

class DetailsBookFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {
    override val viewModel: DetailsViewModel by viewModels()


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val book = arguments?.getParcelable<Data>("book")
        book.let {
            setFieldsText(it)
        }
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setFieldsText(it: Data?) {
        with(binding){
            Glide.with(requireContext())
                .load(it?.imageUrl)
                .placeholder(R.drawable.empty)
                .error(R.drawable.empty)
                .into(imageView)
            txtTitle.text = "Titulo: ${it?.title}"
            txtSummary.text = "Sumario: ${it?.summary}"
            txtAuthor.text = "Autor: ${it?.author}"
            txtCategory.text = "Categoria: ${it?.category?.title}"
            txtCreated.text = "Criado : ${it?.createdAt?.let { it1 -> formatDate(it1) }}"
        }


    }
}