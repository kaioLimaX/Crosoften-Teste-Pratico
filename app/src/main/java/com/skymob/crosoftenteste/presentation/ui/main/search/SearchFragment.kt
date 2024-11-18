package com.skymob.crosoftenteste.presentation.ui.main.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skymob.crosoftenteste.R
import com.skymob.crosoftenteste.databinding.FragmentSearchBinding
import com.skymob.crosoftenteste.presentation.ui.base.BaseFragment
import com.skymob.crosoftenteste.presentation.ui.main.adapter.BookAdapter
import com.skymob.crosoftenteste.presentation.ui.state.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    private lateinit var bookAdapter: BookAdapter
    override val viewModel: SearchViewModel by viewModel()


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initConfigSearchBar()
        initRecyclerView()


    }

    private fun initConfigSearchBar() {
        binding.searchBook.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.updateSearchQuery(newText ?: "")
                return true
            }

        })
        binding.searchBook.setOnCloseListener {
            binding.searchBook.setQuery("", false) // Limpa o campo
            binding.searchBook.clearFocus()
            bookAdapter.updateBooks(emptyList())
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.searchBook.windowToken, 0)

            true // Retorna true para indicar que o evento foi tratado
        }
    }

    private fun initRecyclerView() {
        bookAdapter = BookAdapter (
            showButtonDelete = false,
            onItemClick = {
                navigateToBookDetails(it.id)
            },
            onDeleteClick = {
                viewModel.deleteBook(it.id)
            }

        )
        binding.rvSearch.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookAdapter
        }
    }

    private fun navigateToBookDetails(id: Int) {
        val bundle = Bundle().apply {
            putInt("bookId", id) // Altere o tipo e a chave conforme necessário
        }

        findNavController().navigate(
            R.id.action_listFragment_to_detailsBookFragment,
            bundle,
            NavOptions.Builder()
                .build()
        )
    }


    private fun initObserver() {
        viewModel.searchList.observe(viewLifecycleOwner) { status ->
            bookAdapter.updateBooks(emptyList())
            when (status) {
                is ViewState.Error -> {
                    showProgressBar(false)
                    Toast.makeText(requireContext(), "Ocorreu um erro inesperado, tente novamente", Toast.LENGTH_SHORT).show()
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
            binding.progressSearch.visibility = View.VISIBLE
        } else {
            binding.progressSearch.visibility = View.GONE

        }

    }
}