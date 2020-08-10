package com.example.internshipapp.dashboard.books

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.internshipapp.R
import com.example.internshipapp.dashboard.BooksFragmentViewModel
import com.example.internshipapp.dashboard.models.BookItem
import kotlinx.android.synthetic.main.fragment_add_book.*

class AddBookFragment : Fragment() {
    var booksFragment: BooksFragment = BooksFragment()
    lateinit var booksModel: BooksFragmentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        booksModel = ViewModelProvider(requireActivity()).get(BooksFragmentViewModel::class.java)
        var c: Context? = context
        if (c != null) {
            booksModel.setContext(c)
        }

        addButton.setOnClickListener {
            showProgressButtonAnimation()
            var bookTitle: String = textInputBookTitle.text.toString().trim()
            var bookAuthor: String = textInputBookAuthor.text.toString().trim()
            var bookPublisher: String = textInputPublisher.text.toString().trim()
            var newBook = BookItem(bookTitle, bookAuthor, bookPublisher)
            //dupa add am facut din nou call la metoda init() ca sa fac iar request ul
            booksModel.addBook(newBook)
            booksModel.init()
            redirectUser()
            hideProgressButtonAnimation()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun showProgressButtonAnimation() {
        addButton.startAnimation()
    }

    private fun hideProgressButtonAnimation() {
        addButton.revertAnimation()
        addButton.setBackgroundResource(R.drawable.button)
    }

    private fun redirectUser() {
        parentFragmentManager.popBackStack()
    }
}



