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
        initOnClick()
        viewModel.getBooks()
        initRecyclerView()
    }

    private fun initOnClick() {
        with(binding){
            btnReload.setOnClickListener {
                viewModel.getBooks()
            }
        }
    }

    private fun initRecyclerView() {
        bookAdapter = BookAdapter (
            onItemClick = {
                viewModel.getBookDetails(it.id)

            },
            onDeleteClick = {
                viewModel.deleteBook(it.id)
            }
        )
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
        viewModel.deleteBookStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is ViewState.Error -> {
                    alertLoading.close()
                    Toast.makeText(requireContext(), "Ocorreu um erro inesperado, tente novamente", Toast.LENGTH_SHORT).show()
                    viewModel.resetDeleteStatus()
                }
                is ViewState.Loading -> {
                    alertLoading.show("Deletando livro")
                }
                is ViewState.Sucess -> {
                    alertLoading.close()
                    Toast.makeText(requireContext(), "Livro deletado com sucesso", Toast.LENGTH_SHORT).show()
                    viewModel.getBooks()
                    viewModel.resetDeleteStatus()

                }
                null -> Unit
            }

        }

        viewModel.getBookDetailsStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is ViewState.Error -> {
                    alertLoading.close()
                    Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
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
                    showErrorLayout(true)
                    Toast.makeText(requireContext(), "Erro inesperado ao carregar livros", Toast.LENGTH_SHORT).show()
                }

                is ViewState.Loading -> {
                    showProgressBar(true)
                }

                is ViewState.Sucess -> {
                    showProgressBar(false)
                    showErrorLayout(false)
                    status.data?.let { bookAdapter.updateBooks(it) }
                }
            }

        }
    }

    private fun showErrorLayout(show: Boolean) {
        if (show) {
            binding.rvBooks.visibility = View.GONE
            binding.btnReload.visibility = View.VISIBLE
        } else {
            binding.rvBooks.visibility = View.VISIBLE
            binding.btnReload.visibility = View.GONE
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