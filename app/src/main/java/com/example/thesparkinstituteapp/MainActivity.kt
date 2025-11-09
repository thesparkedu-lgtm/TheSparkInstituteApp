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

        if (!isLoggedIn || user == null) {
            startActivity(Intent(this, Login_Register_Activity::class.java))
            finish()
            return
        }
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

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
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.fade_slide_in_left,
            R.anim.fade_slide_out_left,
            R.anim.fade_slide_in_right,
            R.anim.fade_slide_out_right
        )
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

}
