package com.example.thesparkinstituteapp.Explore

import android.content.Intent
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.thesparkinstituteapp.Courses_Classes.Coaching.Coaching_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Competative.Competative_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Computer.Computer_Fragment
import com.example.thesparkinstituteapp.Courses_Classes.Courses_Classes_Activity
import com.example.thesparkinstituteapp.Courses_Classes.ExtraCourses.ExploreFreeMasterClass
import com.example.thesparkinstituteapp.Courses_Classes.Novodaya.Class_9
import com.example.thesparkinstituteapp.Courses_Classes.Novodaya.Navodaya_Fragment
import com.example.thesparkinstituteapp.MainActivity
import com.example.thesparkinstituteapp.R
import com.example.thesparkinstituteapp.notes_and_Pdf_Reader.notes_Fragment
import kotlin.jvm.java

class Explore_Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore, container, false)

        val ExploreNavodaya = view.findViewById<LinearLayout>(R.id.ExploreNavodaya)
        val ExploreComputer = view.findViewById<LinearLayout>(R.id.ExploreComputer)
        val ExploreCompetative = view.findViewById<LinearLayout>(R.id.ExploreCompetative)
        val ExploreCoaching = view.findViewById<LinearLayout>(R.id.ExploreCoaching)
        val ExploreNotes = view.findViewById<LinearLayout>(R.id.ExploreNotes)
        val ExploreFreeMasterClass = view.findViewById<LinearLayout>(R.id.ExploreFreeMasterClass)

        ExploreNavodaya.setOnClickListener {
          val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad","ExploreNavodaya")
            startActivity(intent)
        }


        ExploreCompetative.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad", "ExploreCompetative")
            startActivity(intent)
        }

        ExploreCoaching.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad", "ExploreCoaching")
            startActivity(intent)
        }

        ExploreComputer.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad", "ExploreComputer")
            startActivity(intent)
        }

        ExploreFreeMasterClass.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad", "ExploreFreeMasterClass")
            startActivity(intent)
        }

        ExploreNotes.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad", "ExploreNotes")
            startActivity(intent)
        }

        return view
    }


}