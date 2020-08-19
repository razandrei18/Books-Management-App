package com.example.internshipapp.dashboard

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.internshipapp.dashboard.models.BookItem
import com.example.internshipapp.dashboard.repositories.BookRepository

class BooksFragmentViewModel(c : Context) : ViewModel() {
    private lateinit var cont: Context
    var books: MutableLiveData<MutableList<BookItem>> = MutableLiveData<MutableList<BookItem>>()
    var triggerAddBook : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var triggerDeleteBook : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var triggerEditBook : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var triggerRefreshBooks : MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var bookRepo: BookRepository = BookRepository()
    var bookItemAdd: BookItem? = null
    var bookItemDelete: BookItem? = null
    var bookItemEdit : BookItem? = null

    init {
        books = bookRepo.booksGetRequest(c)
    }

    var bookNew: LiveData<BookItem> = Transformations.switchMap(triggerAddBook) {
        if (it != null && it)
            addBook()
        else
            null
    }
    var bookRefreshedList: LiveData<MutableList<BookItem>> =Transformations.switchMap(triggerRefreshBooks) {
        if (it != null && it)
            refreshList()
        else
            null
    }

    var deletedBook: LiveData<BookItem> = Transformations.switchMap(triggerDeleteBook) {
        if (it != null && it) {
            removeBook()
        } else
            null
    }

    var editedBook: LiveData<BookItem> = Transformations.switchMap(triggerEditBook) {
        if (it != null && it) {
            editBook()
        } else
            null
    }



    private fun editBook(): LiveData<BookItem> {
        return bookRepo.editBookRequest(cont, bookItemEdit)
    }

    private fun removeBook(): LiveData<BookItem> {
        return bookRepo.deleteBookRequest(cont, bookItemDelete)
    }

    private fun addBook(): LiveData<BookItem> {
        return bookRepo.addBookRequest(cont, bookItemAdd)
    }

    private fun refreshList(): MutableLiveData<MutableList<BookItem>> {
        books.value?.clear()
         return bookRepo.booksGetRequest(cont)
    }

    fun setContext(ctx: Context) {
        this.cont = ctx
    }
}


