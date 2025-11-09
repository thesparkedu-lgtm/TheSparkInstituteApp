package com.example.thesparkinstituteapp.Courses_Classes

import android.R.attr.fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Coaching.Coaching_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Competative.Competative_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Computer.Computer_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Novodaya.Navodaya_Fragment
import com.example.thesparkinstituteapp.Home.HomeFragment
import com.example.thesparkinstituteapp.MainActivity
import com.example.thesparkinstituteapp.R
import com.example.thesparkinstituteapp.loginandregister.Login_Register_Activity
import kotlin.jvm.java

class Courses_Classes_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_courses_classes)

        val fragmentTag = intent.getStringExtra("fragmentToLoad")

        val fragment = when(fragmentTag){
            "Navodaya" -> Navodaya_Fragment()
            "Coaching" -> Coaching_Fragment()
            "Computer" -> Computer_Fragment()
            "Competative" -> Competative_Fragment()
            else -> Navodaya_Fragment()
        }
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_slide_out_right,
                R.anim.fade_slide_in_right,
                R.anim.fade_slide_out_left,
                R.anim.fade_slide_in_left
            )
            .replace(R.id.fragment_container_2,fragment)
            .commit()
    }
}