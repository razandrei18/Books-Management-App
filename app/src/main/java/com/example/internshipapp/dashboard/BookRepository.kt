package com.example.internshipapp.dashboard

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.internshipapp.login.LoginInteractor.SHARED_PREFS
import com.example.internshipapp.login.LoginInteractor.TEXT
import org.json.JSONArray
import org.json.JSONObject

class BookRepository {
    val liveBookData = MutableLiveData<List<BookItem>>()
    val url = "https://ancient-earth-13943.herokuapp.com/api/books/"
    val bookData: ArrayList<BookItem> = ArrayList()

    fun booksGetRequest(c: Context): MutableLiveData<List<BookItem>> {

        val requestQueue = Volley.newRequestQueue(c)
        val sharedPref = c.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE).getString(TEXT, "")
        Log.i("EDITTT", sharedPref)
        val  jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    var jsonArray: JSONArray = JSONArray(response)
                    for (item in 1..jsonArray.length()) {
                        var bookObject: JSONObject = jsonArray.getJSONObject(item)
                        var title: String = bookObject.getString("title")
                        var author: String = bookObject.getString("author")
                        var publisher: String = bookObject.getString("publisher")
                        var bookItem: BookItem = BookItem(title, author, publisher)
                        bookData.add(bookItem)
                    }
                    liveBookData.setValue(bookData)

                },
                Response.ErrorListener { error ->
                    Log.i("TAGGG", error.networkResponse.statusCode.toString())
                })
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer $sharedPref");
                return headers
            }
        }

        requestQueue.add(jsonObjectRequest)
        return liveBookData
    }


}

