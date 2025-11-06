package com.example.thesparkinstituteapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.thesparkinstituteapp.Explore.Explore_Fragment
import com.example.thesparkinstituteapp.Home.HomeFragment
import com.example.thesparkinstituteapp.Profile.ProfileFragment
import com.example.thesparkinstituteapp.loginandregister.LoginFragment
import com.example.thesparkinstituteapp.sharedPre.SharedPrefHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPrefHelper = SharedPrefHelper(this)

        val isLoggedIn = sharedPrefHelper.isLoggedIn()
        val user = FirebaseAuth.getInstance().currentUser

        if (isLoggedIn && user != null) {
            replaceFragment(HomeFragment())
        } else {
            replaceFragment(LoginFragment())
        }

        val bottomNav = findViewById<BottomNavigationView?>(R.id.bottomNavigationView)
        loadFragment(HomeFragment()) // default

        bottomNav.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item: MenuItem? ->
            val id = item!!.getItemId()
            val selectedFragment: Fragment?

            if (id == R.id.NevHome) {
                selectedFragment = HomeFragment()
            } else if (id == R.id.NevExplore) {
                selectedFragment = Explore_Fragment()
            } else {
                selectedFragment = ProfileFragment()
            }


            loadFragment(selectedFragment!!)
            true
        })
        return
    }

    private fun loadFragment(fragment: Fragment) {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
    }




