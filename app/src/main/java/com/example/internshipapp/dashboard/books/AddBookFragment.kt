package com.example.internshipapp.dashboard.books

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.internshipapp.R
import com.example.internshipapp.dashboard.BooksFragmentViewModel
import com.example.internshipapp.dashboard.models.BookItem
import kotlinx.android.synthetic.main.fragment_add_book.*

class AddBookFragment : Fragment() {
    lateinit var booksModel: BooksFragmentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_book, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        booksModel = ViewModelProvider(requireActivity()).get(BooksFragmentViewModel::class.java)

        addButton.setOnClickListener {
            showProgressButtonAnimation()
            var bookTitle: String = textInputBookTitle.text.toString().trim()
            var bookAuthor: String = textInputBookAuthor.text.toString().trim()
            var bookPublisher: String = textInputPublisher.text.toString().trim()
            var bookId = ""
            booksModel.bookItemAdd = BookItem(bookTitle, bookAuthor, bookPublisher, bookId)
            booksModel.triggerAddBook.value = true
        }

        booksModel.bookNew.observe(viewLifecycleOwner, Observer {
            hideProgressButtonAnimation()
            if (booksModel.triggerAddBook.value!!) {
                if (it != null) {
                    booksModel.triggerAddBook.value = false
                    booksModel.bookItemAdd = null
                    redirectUser()
                } else {
                    showAddBookErrorMessage()
                    hideProgressButtonAnimation()
                    redirectUser()
                }
            }
        })
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

    private fun showAddBookErrorMessage() {
        Toast.makeText(context, getString(R.string.addBookErrorMessage), Toast.LENGTH_SHORT).show()
    }
}



