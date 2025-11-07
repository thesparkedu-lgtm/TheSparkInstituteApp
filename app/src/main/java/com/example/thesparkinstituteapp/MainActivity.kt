package com.example.thesparkinstituteapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.thesparkinstituteapp.Explore.Explore_Fragment
import com.example.thesparkinstituteapp.Home.HomeFragment
import com.example.thesparkinstituteapp.Profile.ProfileFragment
import com.example.thesparkinstituteapp.loginandregister.Login_Register_Activity
import com.example.thesparkinstituteapp.sharedPre.SharedPrefHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPrefHelper = SharedPrefHelper(this)
        bottomNav = findViewById(R.id.bottomNavigationView)

        val isLoggedIn = sharedPrefHelper.isLoggedIn()
        val user = FirebaseAuth.getInstance().currentUser

        // ✅ If not logged in → go to LoginActivity
        if (!isLoggedIn || user == null) {
            startActivity(Intent(this, Login_Register_Activity::class.java))
            finish()
            return
        }

        // ✅ Otherwise, stay in MainActivity
        loadFragment(HomeFragment()) // default fragment

        bottomNav.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item: MenuItem ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.NevHome -> HomeFragment()
                R.id.NevExplore -> Explore_Fragment()
                R.id.NevProfile -> ProfileFragment()
                else -> HomeFragment()
            }
            loadFragment(selectedFragment)
            true
        })
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
