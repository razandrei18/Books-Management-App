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
    var bookNew : MutableLiveData<BookItem> = MutableLiveData<BookItem>()

    fun init(): MutableLiveData<List<BookItem>> {
        if (books.value.isNullOrEmpty()) {
            books = bookRepo.booksGetRequest(cont)
            return books
        } else {
            return books
        }
    }

    fun setContext(ctx: Context) {
        this.cont = ctx
    }

    fun addBook(book: BookItem) {
        bookNew = bookRepo.addBookRequest(cont, book)
    }
}