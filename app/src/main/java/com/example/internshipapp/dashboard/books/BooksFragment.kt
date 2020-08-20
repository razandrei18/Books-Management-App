package com.example.internshipapp.dashboard.books

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internshipapp.dashboard.BooksFragmentViewModel
import com.example.internshipapp.R
import com.example.internshipapp.dashboard.BooksFragmentViewModelFactory
import com.example.internshipapp.dashboard.models.BookItem
import kotlinx.android.synthetic.main.fragment_books.*


class BooksFragment : Fragment(), BooksAdapter.OnDeleteBtnClicked, BooksAdapter.OnEditBtnClicked {
    lateinit var booksModel: BooksFragmentViewModel
    var position: Int = 0

    private var adapter: BooksAdapter = BooksAdapter(this, this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bookViewModelFactory = BooksFragmentViewModelFactory(requireContext())
        booksModel = ViewModelProvider(requireActivity(), bookViewModelFactory).get(BooksFragmentViewModel::class.java)
        booksLoading_progressBar.visibility = View.VISIBLE

        recyclerView_books.layoutManager = LinearLayoutManager(context)
        recyclerView_books.adapter = adapter

        swipeRefresh_layout.setOnRefreshListener {
            booksModel.triggerRefreshBooks.value = true
        }

        //refresh List Observe
        booksModel.bookRefreshedList.observe(viewLifecycleOwner, Observer {
            if (booksModel.triggerRefreshBooks.value!!) {
                if (it != null) {
                    adapter.setData(it)
                    swipeRefresh_layout.isRefreshing = false
                    booksModel.triggerRefreshBooks.value = false
                    adapter.notifyDataSetChanged()
                }
            }
        })

        //booksList Observe
        booksModel.books.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                recyclerView_books.recycledViewPool.clear()
                adapter.setData(it)
                adapter.notifyDataSetChanged();
                hideProgressBar()
            } else {
                showErrorMessage()
                hideProgressBar()
            }
        })

        //deleted book Observe
        booksModel.deletedBook.observe(viewLifecycleOwner, Observer {
            if (booksModel.triggerDeleteBook.value!!) {
                if (it != null) {
                    booksModel.triggerDeleteBook.value = false
                    booksModel.bookItemDelete = null
                    adapter.notifyDataSetChanged()
                }
            }
        })

        booksModel.editedBook.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                adapter.updateData(position, it)
                adapter.notifyItemChanged(position)
            }
        })

        recyclerView_books.setHasFixedSize(true)

        addBook_button.setOnClickListener {
            var addBookFragment = AddBookFragment()
            openAddNewBookFragment(addBookFragment)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun hideProgressBar() {
        booksLoading_progressBar.visibility = View.INVISIBLE
    }

    private fun showErrorMessage() {
        Toast.makeText(context, getString(R.string.getBooksErrorMessage), Toast.LENGTH_SHORT).show()
    }

    private fun openAddNewBookFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment).addToBackStack("addBookFrag")
            commit()
        }
    }

    override fun onDeleteClick(bookItem: BookItem, position: Int) {
        booksModel.bookItemDelete = bookItem
        booksModel.triggerDeleteBook.value = true
        adapter.deleteData(position)
        adapter.notifyItemRemoved(position)
    }

    override fun onEditClick(bookItem: BookItem, position: Int) {
        this.position = position
        booksModel.bookItemEdit = bookItem
        var editBookFragment = EditBookFragment()
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, editBookFragment).addToBackStack("editBookFrag")
            commit()
        }
    }
}


