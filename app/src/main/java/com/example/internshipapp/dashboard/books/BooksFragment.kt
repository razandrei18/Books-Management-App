package com.example.internshipapp.dashboard.books

import android.content.Context
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internshipapp.dashboard.BooksFragmentViewModel
import com.example.internshipapp.R
import com.example.internshipapp.dashboard.models.BookItem
import kotlinx.android.synthetic.main.book_item.*
import kotlinx.android.synthetic.main.fragment_books.*


class BooksFragment : Fragment() {
    lateinit var booksModel: BooksFragmentViewModel
    var adapter: BooksAdapter = BooksAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        booksModel = ViewModelProvider(requireActivity()).get(BooksFragmentViewModel::class.java)
        booksLoading_progressBar.visibility = View.VISIBLE
        var c: Context? = context
        if (c != null) {
            booksModel.setContext(c)
        }

        recyclerView_books.layoutManager = LinearLayoutManager(context)

        if (booksModel.books.value.isNullOrEmpty()) {
            booksModel.init()
            booksModel.books.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    Log.i("IFTAGGG", "Am intrat pe if")
                    Log.i("IFTAGGG", it.toString())
                    it.dropLast(80)
                    adapter.setData(it)
                    recyclerView_books.adapter = adapter
                    hideProgressBar()
                } else {
                    showErrorMessage()
                    hideProgressBar()
                }
            })
        }
        else{
            booksModel.books.observe(viewLifecycleOwner, Observer {
                Log.i("ELSETAGGG", it.toString())
                booksModel.init()
                adapter.setData(it)
                recyclerView_books.adapter = adapter
                hideProgressBar()
            })
        }

        recyclerView_books.setHasFixedSize(true)
        addBook_button.setOnClickListener {
            var addBookFragment = AddBookFragment()
            openAddNewBookFragment(addBookFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun hideProgressBar() {
        booksLoading_progressBar.visibility = View.INVISIBLE
    }

    fun showErrorMessage() {
        Toast.makeText(context, getString(R.string.getBooksErrorMessage), Toast.LENGTH_SHORT).show()
    }

    private fun openAddNewBookFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment).addToBackStack("addBookFrag")
            commit()
        }
    }

}


