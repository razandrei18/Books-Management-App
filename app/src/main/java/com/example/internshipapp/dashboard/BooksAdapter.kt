package com.example.internshipapp.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.internshipapp.R
import kotlinx.android.synthetic.main.book_item.view.*

class BooksAdapter() : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    private var booksList: List<BookItem> = ArrayList()

    class BooksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textviewBookTitle: TextView = itemView.textView_BookTitle
        val textviewBookAuthor: TextView = itemView.textView_BookAuthor
        val textviewBookPublisher: TextView = itemView.textView_BookPublisher
    }

    fun setData(list: List<BookItem>) {
        this.booksList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return BooksViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val currentBook = booksList[position]

        holder.textviewBookTitle.setText(currentBook.bookTitle)
        holder.textviewBookAuthor.setText(currentBook.bookAuthor)
        holder.textviewBookPublisher.setText(currentBook.bookPublisher)

    }

    override fun getItemCount() = booksList.size


}