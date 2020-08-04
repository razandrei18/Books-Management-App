package com.example.internshipapp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.internshipapp.dashboard.BookItem
import com.example.internshipapp.dashboard.BookRepository
import com.example.internshipapp.dashboard.Fragments.BooksFragment

class BooksFragmentViewModel() : ViewModel() {
    lateinit var cont : Context
    var books: MutableLiveData<List<BookItem>> = MutableLiveData<List<BookItem>>()
    var bookRepo: BookRepository = BookRepository()
    fun init() {
        if (books.value != null)
            return
        books = bookRepo.booksGetRequest(cont)
    }
    fun setContext(ctx : Context){
        this.cont = ctx
    }
}