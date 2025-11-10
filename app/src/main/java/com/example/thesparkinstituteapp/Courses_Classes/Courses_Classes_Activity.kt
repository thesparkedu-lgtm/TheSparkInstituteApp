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
import com.example.thesparkinstituteapp.Courses_Classes.Computer.ADCA_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Computer.Android_Development
import com.example.thesparkinstituteapp.Courses_Classes.Computer.Computer_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Computer.DCA_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Computer.Graphic_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Computer.Java_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Computer.Kotlin_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Computer.Video_Editing_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Computer.Web_design
import com.example.thesparkinstituteapp.Courses_Classes.ExtraCourses.ExploreFreeMasterClass
import com.example.thesparkinstituteapp.Courses_Classes.ExtraCourses.ExploreNotes
import com.example.thesparkinstituteapp.Courses_Classes.ExtraCourses.Notes
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

        val fragment = when (fragmentTag) {
            "Navodaya" -> Navodaya_Fragment()
            "Coaching" -> Coaching_Fragment()
            "Computer" -> Computer_Fragment()
            "Competative" -> Competative_Fragment()
            "ExploreNavodaya" -> Navodaya_Fragment()
            "ExploreCoaching" -> Coaching_Fragment()
            "ExploreComputer" -> Computer_Fragment()
            "ExploreCompetative" -> Competative_Fragment()
            "ExploreNotes" -> ExploreNotes()
            "ExploreFreeMasterClass" -> ExploreFreeMasterClass()
            "notesBtn" -> Notes()
            "ADCA" -> ADCA_Fragment()
            "DCA" -> DCA_Fragment()
            "Java" -> Java_Fragment()
            "Kotlin" -> Kotlin_Fragment()
            "Android_Development" -> Android_Development()
            "VideoEditing" -> Video_Editing_Fragment()
            "Graphic" -> Graphic_Fragment()
            "webDesign" -> Web_design()
            else -> Navodaya_Fragment()
        }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_slide_out_right,
                R.anim.fade_slide_in_right,
                R.anim.fade_slide_out_left,
                R.anim.fade_slide_in_left
            )
            .replace(R.id.fragment_container_2, fragment)
            .commit()
    }
}