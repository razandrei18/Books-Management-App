package com.example.internshipapp.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.internshipapp.R
import com.example.internshipapp.dashboard.books.BooksFragment
import com.example.internshipapp.dashboard.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val booksFragment = BooksFragment()
    val profileFragment = ProfileFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setCurrentFragment(booksFragment)

        navBottom_id.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_books -> setCurrentFragment(booksFragment)
                R.id.navigation_profile -> setCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment, fragment).addToBackStack("currentFragment")
            commit()
        }
    }
}
