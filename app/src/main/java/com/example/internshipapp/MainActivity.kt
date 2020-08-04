package com.example.internshipapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.internshipapp.dashboard.Fragments.BooksFragment
import com.example.internshipapp.dashboard.Fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val booksFragment = BooksFragment()
        val profileFragment = ProfileFragment()
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
            replace(R.id.main_fragment, fragment)
            commit()
        }
    }
}
