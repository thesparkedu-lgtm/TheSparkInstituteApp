package com.example.thesparkinstituteapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.thesparkinstituteapp.Home.HomeFragment
import com.example.thesparkinstituteapp.loginandregister.LoginFragment
import com.example.thesparkinstituteapp.sharedPre.SharedPrefHelper
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
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
