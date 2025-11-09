package com.example.thesparkinstituteapp.Courses_Classes.ExtraCourses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thesparkinstituteapp.R

class ExploreFreeMasterClass : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore_free_master_class, container, false)



        return view

    }

}