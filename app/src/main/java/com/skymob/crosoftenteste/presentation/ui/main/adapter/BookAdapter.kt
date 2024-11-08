package com.skymob.crosoftenteste.presentation.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skymob.crosoftenteste.data.remote.dto.book.Data
import com.skymob.crosoftenteste.databinding.ItemBookBinding

class BookAdapter(
    private val onItemClick: (Data) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var books = listOf<Data>()

    inner class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Data) {
            binding.txtTitulo.text = book.title
            binding.txtSumario.text = book.summary
            binding.txtAutor.text = book.author
            binding.txtCategoria.text = book.category!!.title
            binding.root.setOnClickListener { onItemClick(book) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount() = books.size

    // Atualiza a lista usando DiffUtil
    fun updateBooks(newBooks: List<Data>) {
        val diffCallback = BooksDiffCallback(books, newBooks)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        books = newBooks
        diffResult.dispatchUpdatesTo(this)
    }

    // Classe DiffUtil.Callback para calcular a diferença entre as listas
    class BooksDiffCallback(
        private val oldList: List<Data>,
        private val newList: List<Data>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}