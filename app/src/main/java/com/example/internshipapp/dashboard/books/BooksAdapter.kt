package com.example.internshipapp.dashboard.books

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.internshipapp.R
import com.example.internshipapp.dashboard.BooksFragmentViewModel
import com.example.internshipapp.dashboard.models.BookItem
import kotlinx.android.synthetic.main.book_item.view.*

class BooksAdapter(var deleteClickListener: OnDeleteBtnClicked) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    private var booksList: List<BookItem> = ArrayList()

    class BooksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textviewBookTitle: TextView = itemView.textView_BookTitle
        val textviewBookAuthor: TextView = itemView.textView_BookAuthor
        val textviewBookPublisher: TextView = itemView.textView_BookPublisher
        private val deleteBtn: Button = itemView.deleteBook_btn

        fun delete(bookItem: BookItem, action: OnDeleteBtnClicked) {
            deleteBtn.setOnClickListener {
                action.onDeleteClick(bookItem, adapterPosition)
            }
        }
    }

    fun setData(list: List<BookItem>) {
        this.booksList = list
        notifyDataSetChanged()
    }

    fun deleteData(position: Int) {
        booksList.drop(position)
        notifyItemRemoved(position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)

        return BooksViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val currentBook = booksList[position]
        holder.textviewBookTitle.text = "Title: " + currentBook.bookTitle
        holder.textviewBookAuthor.text = "Author: " + currentBook.bookAuthor
        holder.textviewBookPublisher.text = "Publisher: " + currentBook.bookPublisher
        holder.delete(booksList[position], deleteClickListener)
    }

    override fun getItemCount() = booksList.size

    interface OnDeleteBtnClicked {
        fun onDeleteClick(bookItem: BookItem, position: Int)
    }
}