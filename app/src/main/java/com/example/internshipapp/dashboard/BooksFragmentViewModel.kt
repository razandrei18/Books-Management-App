package com.example.internshipapp.dashboard

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.internshipapp.dashboard.books.BooksAdapter
import com.example.internshipapp.dashboard.models.BookItem
import com.example.internshipapp.dashboard.repositories.BookRepository
import kotlinx.android.synthetic.main.fragment_books.*

class BooksFragmentViewModel() : ViewModel() {

    lateinit var cont: Context
    var books: MutableLiveData<List<BookItem>> = MutableLiveData<List<BookItem>>()
    var bookRepo: BookRepository = BookRepository()


    fun init() {
        if (books.value.isNullOrEmpty()) {
            books = bookRepo.booksGetRequest(cont)
        } else {
            books.value = emptyList()
        }
    }

    fun setContext(ctx: Context) {
        this.cont = ctx
    }

    fun addBook(book: BookItem) {
        bookRepo.addBookRequest(cont, book)
    }
}