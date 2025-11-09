package com.example.thesparkinstituteapp.Home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Coaching.Coaching_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Competative.Competative_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Computer.Computer_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Courses_Classes_Activity
import com.example.thesparkinstituteapp.R
import com.example.thesparkinstituteapp.sharedPre.SharedPrefHelper
import kotlin.jvm.java

class HomeFragment : Fragment() {

    private lateinit var prefs: SharedPrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val navodayaBtn = view.findViewById<LinearLayout>(R.id.NavodayaBtn)
        val competativaBtn = view.findViewById<LinearLayout>(R.id.CompetitiveBtn)
        val computerBtn = view.findViewById<LinearLayout>(R.id.ComputerBtn)
        val coachingBtn = view.findViewById<LinearLayout>(R.id.CoachingBtn)

        navodayaBtn.setOnClickListener {

            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad", "Navodaya")
            startActivity(intent)

        }

        competativaBtn.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad", "Competative")
            startActivity(intent)
        }

        computerBtn.setOnClickListener {
         val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad", "Computer")
            startActivity(intent)
        }

        coachingBtn.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad", "Coaching")
            startActivity(intent)
        }

        return view
    }
}
