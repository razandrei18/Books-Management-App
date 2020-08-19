package com.example.internshipapp.dashboard.books

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.internshipapp.R
import com.example.internshipapp.dashboard.models.BookItem
import kotlinx.android.synthetic.main.book_item.view.*

class BooksAdapter(var deleteClickListener: OnDeleteBtnClicked, var editClickListener: OnEditBtnClicked) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    private var booksList: MutableList<BookItem> = ArrayList()

    class BooksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textviewBookTitle: TextView = itemView.textView_BookTitle
        val textviewBookAuthor: TextView = itemView.textView_BookAuthor
        val textviewBookPublisher: TextView = itemView.textView_BookPublisher
        private val deleteBtn: Button = itemView.deleteBook_btn
        private val editBtn = itemView.editBook_btn

        fun delete(bookItem: BookItem, action: OnDeleteBtnClicked) {
            deleteBtn.setOnClickListener {
                action.onDeleteClick(bookItem, adapterPosition)
            }
        }

        fun edit(bookItem: BookItem, action: OnEditBtnClicked){
            editBtn.setOnClickListener {
                action.onEditClick(bookItem, adapterPosition)
            }
        }
    }

    fun setData(list: MutableList<BookItem>) {
        this.booksList = list
        notifyDataSetChanged()
    }

    fun deleteData(position: Int) {
        booksList.drop(position)
        notifyDataSetChanged()
    }

    fun updateData(position: Int, bookItem: BookItem?){
        if (bookItem != null) {
            booksList[position] = bookItem
        }
        notifyItemChanged(position)
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
        holder.edit(booksList[position], editClickListener)
    }

    override fun getItemCount(): Int { return booksList.size }

    interface OnDeleteBtnClicked {
        fun onDeleteClick(bookItem: BookItem, position: Int)
    }

    interface OnEditBtnClicked{
        fun onEditClick(bookItem: BookItem, position: Int)
    }
}