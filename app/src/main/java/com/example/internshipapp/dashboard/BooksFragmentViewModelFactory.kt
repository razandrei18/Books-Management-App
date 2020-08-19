package com.example.internshipapp.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BooksFragmentViewModelFactory (val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BooksFragmentViewModel::class.java)) {
            return BooksFragmentViewModel(context) as T
        } else {
            throw RuntimeException("Failed to create instance of the ${modelClass.simpleName}")
        }
    }
}