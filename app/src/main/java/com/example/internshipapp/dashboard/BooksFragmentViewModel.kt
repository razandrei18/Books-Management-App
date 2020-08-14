package com.example.internshipapp.dashboard

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.internshipapp.dashboard.models.BookItem
import com.example.internshipapp.dashboard.repositories.BookRepository

class BooksFragmentViewModel() : ViewModel() {

    lateinit var cont: Context
    var books: MutableLiveData<List<BookItem>> = MutableLiveData<List<BookItem>>()
    var triggerAddBook = MutableLiveData<Boolean>()
    var triggerDeleteBook = MutableLiveData<Boolean>()
    var bookRepo: BookRepository = BookRepository()
    var bookItem: BookItem? = null
    var bookItemD: BookItem? = null
    var bookNew: LiveData<BookItem> = Transformations.switchMap(triggerAddBook) {
        if (it != null && it)
            addBook()
        else
            null
    }
    var deletedBook: LiveData<BookItem> = Transformations.switchMap(triggerDeleteBook) {
        if (it != null && it) {
            removeBook()
        } else
            null
    }

    private fun removeBook(): LiveData<BookItem> {
        return bookRepo.deleteBookRequest(cont, bookItemD)
    }

    private fun addBook(): LiveData<BookItem> {
        return bookRepo.addBookRequest(cont, bookItem)
    }

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
}