package com.example.internshipapp.dashboard.books

import android.content.Context
import android.os.Bundle
import android.text.method.TextKeyListener.clear
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
import com.example.internshipapp.dashboard.models.BookItem
import kotlinx.android.synthetic.main.fragment_books.*


class BooksFragment : Fragment() {
    lateinit var booksModel: BooksFragmentViewModel
    var adapter: BooksAdapter = BooksAdapter()
    var booksList: List<BookItem> = emptyList()
    var emptyList: List<BookItem> = emptyList()
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

        /*Deci ca idee, dupa logare ma duce la pagina de books, imi arata ok cartile, apoi daca fac add imi adauga cartea, ma redirectioneaza iar la books, dar lista noua (cea care contine si cartea adaugata) o adauga in plus la cea
        veche, nu o sterge intai pe cea veche astfel incat sa am doar o lista.*/

        //aici verific daca e null ca in momentul in care daca am apelat deja odata metoda init sa nu o reapeleze daca valoarea nu s-a schimbat
        if (booksModel.books.value.isNullOrEmpty()) {
            //am incercat sa setez de fiecare data recyclerView-ului sa fie gol, astfel incat sa nu imi adauge valorile peste si sa am 2 liste
            adapter.setData(emptyList)
            recyclerView_books.adapter = adapter
            booksModel.init()
            booksModel.books.observe(viewLifecycleOwner, Observer<List<BookItem>>() {
                recyclerView_books.layoutManager = LinearLayoutManager(context)
                if (it != null) {
                    booksList = it
                    adapter.setData(it)
                    recyclerView_books.adapter = adapter
                    hideProgressBar()
                } else {
                    showErrorMessage()
                    hideProgressBar()
                }
            })
        } else {
            adapter.setData(booksList)
            recyclerView_books.adapter = adapter
            hideProgressBar()
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


