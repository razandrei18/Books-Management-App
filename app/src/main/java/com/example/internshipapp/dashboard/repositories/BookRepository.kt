package com.example.internshipapp.dashboard.repositories

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.internshipapp.dashboard.models.BookItem
import com.example.internshipapp.login.LoginInteractor.SHARED_PREFS
import com.example.internshipapp.login.LoginInteractor.TEXT
import org.json.JSONArray
import org.json.JSONObject

class BookRepository {
    var liveBookData = MutableLiveData<List<BookItem>>()
    var liveAddBookData = MutableLiveData<BookItem>()
    val getBooksurl = "https://ancient-earth-13943.herokuapp.com/api/books/"
    val addBookUrl = "https://ancient-earth-13943.herokuapp.com/api/books/"
    var bookData: ArrayList<BookItem> = ArrayList()


    fun booksGetRequest(c: Context): MutableLiveData<List<BookItem>> {

        val requestQueue = Volley.newRequestQueue(c)
        val sharedPref = c.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE).getString(TEXT, "")
        Log.i("EDITTT", sharedPref)
        var jsonArrayRequest = object : JsonArrayRequest(Request.Method.GET, getBooksurl, null,
                Response.Listener { response ->
                    var jsonArray: JSONArray = response
                    for (item in 0 until jsonArray.length()) {
                        var bookObject: JSONObject = jsonArray.getJSONObject(item)
                        var title: String = bookObject.getString("title")
                        var author: String = bookObject.getString("author")
                        var publisher: String = bookObject.getString("publisher")
                        var bookItem = BookItem(title, author, publisher)
                        bookData.add(bookItem)
                    }
                    liveBookData.setValue(bookData)

                },
                Response.ErrorListener { error ->
                    Log.i("getBooksERR", error.toString())
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "$sharedPref");
                return headers
            }
        }

        requestQueue.add(jsonArrayRequest)
        return liveBookData
    }

    fun addBookRequest(c: Context, newBookItem: BookItem): MutableLiveData<BookItem> {
        val addBookRequestQueue = Volley.newRequestQueue(c)
        val sharedPref = c.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE).getString(TEXT, "")
        var JSONObject = object : StringRequest(Method.POST, addBookUrl,
                Response.Listener { response ->
                    Log.i("ADDBOOK", response)
                    var bTitle  = newBookItem.bookTitle
                    var bAuthor  = newBookItem.bookPublisher
                    var bPublisher  = newBookItem.bookAuthor
                    var addedBook  = BookItem(bTitle,bAuthor,bPublisher)
                    bookData.add(addedBook)
                    Log.i("ADDBOOK", addedBook.toString())
                    liveAddBookData.setValue(addedBook)
                },
                Response.ErrorListener { error ->
                    Log.i("ADDBOOK", error.networkResponse.data.toString())
                }) {
            override fun getParams(): MutableMap<String, String> {
                var newBookParams : MutableMap<String, String> = HashMap()
                newBookParams.put("title", newBookItem.bookTitle)
                newBookParams.put("author", newBookItem.bookAuthor)
                newBookParams.put("publisher", newBookItem.bookPublisher)
                return newBookParams
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "$sharedPref";
                return headers
            }
        }
        addBookRequestQueue.add(JSONObject)
        return liveAddBookData
    }
}



