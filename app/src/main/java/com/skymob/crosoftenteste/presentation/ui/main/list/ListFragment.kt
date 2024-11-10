package com.skymob.crosoftenteste.presentation.ui.main.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skymob.crosoftenteste.R
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.databinding.FragmentListBookBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment
import com.skymob.crosoftenteste.presentation.ui.main.adapter.BookAdapter
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import com.skymob.crosoftenteste.util.AlertLoading
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : BaseFragment<FragmentListBookBinding, ListViewModel>() {

    private lateinit var bookAdapter: BookAdapter
    override val viewModel: ListViewModel by viewModel()
    private val alertLoading by lazy {
        AlertLoading(requireContext())
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListBookBinding = FragmentListBookBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        bookAdapter = BookAdapter { book ->
            viewModel.getBookDetails(book.id)
        }
        binding.rvBooks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookAdapter
        }
    }

    private fun navigateToBookDetails(book : Data) {
        val bundle = Bundle().apply {
            putParcelable("book", book) // Altere o tipo e a chave conforme necessário
        }

        findNavController().navigate(
            R.id.action_listFragment_to_detailsBookFragment,
            bundle,
            NavOptions.Builder()
                .build()
        )
    }

    private fun initObserver() {
        viewModel.getBookDetailsStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is ViewState.Error -> {
                    alertLoading.close()
                    Toast.makeText(requireContext(), status.message, Toast.LENGTH_SHORT).show()
                    viewModel.resetBookDetailsStatus()
                }
                is ViewState.Loading -> {
                    alertLoading.show("Carregando detalhes")
                }
                is ViewState.Sucess -> {
                    alertLoading.close()
                    status.data?.let { navigateToBookDetails(it) }
                    viewModel.resetBookDetailsStatus()
                }

                null -> Unit
            }
        }
        viewModel.listBooksStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is ViewState.Error -> {
                    showProgressBar(false)
                    Toast.makeText(requireContext(), status.message, Toast.LENGTH_SHORT).show()
                }

                is ViewState.Loading -> {
                    showProgressBar(true)
                }

                is ViewState.Sucess -> {
                    showProgressBar(false)
                    status.data?.let { bookAdapter.updateBooks(it) }
                }
            }

        }
    }

    private fun showProgressBar(value: Boolean) {
        if (value) {
            binding.progressList.visibility = View.VISIBLE
        } else {
            binding.progressList.visibility = View.GONE

        }

    }
}