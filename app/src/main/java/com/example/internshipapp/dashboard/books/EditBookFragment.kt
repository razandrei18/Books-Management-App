package com.example.internshipapp.dashboard.books

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.internshipapp.R
import com.example.internshipapp.dashboard.BooksFragmentViewModel
import com.example.internshipapp.dashboard.models.BookItem
import kotlinx.android.synthetic.main.fragment_add_book.*
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.android.synthetic.main.fragment_edit_book.*


class EditBookFragment : Fragment() {
    lateinit var booksModel: BooksFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        booksModel = ViewModelProvider(requireActivity()).get(BooksFragmentViewModel::class.java)

        textInputEditBookTitle.setText(booksModel.bookItemEdit?.bookTitle.toString())
        textInputEditBookAuthor.setText(booksModel.bookItemEdit?.bookAuthor.toString())
        textInputEditPublisher.setText(booksModel.bookItemEdit?.bookPublisher.toString())

        editAddButton.setOnClickListener {
            showProgressButtonAnimation()
            var bookTitle: String = textInputEditBookTitle.text.toString().trim()
            var bookAuthor: String = textInputEditBookAuthor.text.toString().trim()
            var bookPublisher: String = textInputEditPublisher.text.toString().trim()
            var bookId = booksModel.bookItemEdit?.bookId.toString()
            booksModel.bookItemEdit = BookItem(bookTitle, bookAuthor, bookPublisher, bookId)
            booksModel.triggerEditBook.value = true
        }

        booksModel.editedBook.observe(viewLifecycleOwner, Observer {
            if (booksModel.triggerEditBook.value!!) {
                if (it != null) {
                    booksModel.triggerEditBook.value = false
                    booksModel.bookItemEdit = null
                    hideProgressButtonAnimation()
                    redirectUser()
                } else {
                    showEditBookErrorMessage()
                    hideProgressButtonAnimation()
                    redirectUser()
                }
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }


    private fun showProgressButtonAnimation() {
        editAddButton.startAnimation()
    }

    private fun hideProgressButtonAnimation() {
        editAddButton.revertAnimation()
        editAddButton.setBackgroundResource(R.drawable.button)
    }

    private fun redirectUser() {
        parentFragmentManager.popBackStack()
    }
    private fun showEditBookErrorMessage() {
        Toast.makeText(context, getString(R.string.editBookErrorMessage), Toast.LENGTH_SHORT).show()
    }
}