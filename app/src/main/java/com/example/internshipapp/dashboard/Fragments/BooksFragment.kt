package com.example.internshipapp.dashboard.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internshipapp.BooksFragmentViewModel
import com.example.internshipapp.R
import com.example.internshipapp.dashboard.BookItem
import com.example.internshipapp.dashboard.BooksAdapter
import kotlinx.android.synthetic.main.fragment_books.*


class BooksFragment : Fragment() {
    var adapter : BooksAdapter =  BooksAdapter()
    val booksModel: BooksFragmentViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        booksLoading_progressBar.visibility = View.VISIBLE
        var c : Context? = context
        if (c != null) {
            booksModel.setContext(c)
        }
        booksModel.init()
        booksModel.books.observe(viewLifecycleOwner, Observer<List<BookItem>>() {
            if (it != null) {
                adapter.setData(it)
                recyclerView_books.layoutManager = LinearLayoutManager(context)
                recyclerView_books.adapter = adapter
                hideProgressBar()
            }
            else{
                showErrorMessage()
                hideProgressBar()
            }
        })


        recyclerView_books.setHasFixedSize(true)

        super.onViewCreated(view, savedInstanceState)
    }

     fun hideProgressBar(){
        booksLoading_progressBar.visibility = View.INVISIBLE
    }

    fun showErrorMessage(){
        Toast.makeText(context, getString(R.string.getBooksErrorMessage), Toast.LENGTH_SHORT).show()
    }

}


