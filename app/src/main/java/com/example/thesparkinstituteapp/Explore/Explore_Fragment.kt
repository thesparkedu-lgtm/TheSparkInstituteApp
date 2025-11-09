package com.example.thesparkinstituteapp.Explore

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.thesparkinstituteapp.Courses_Classes.Novodaya.Class_9
import com.example.thesparkinstituteapp.Courses_Classes.Novodaya.Navodaya_Fragment
import com.example.thesparkinstituteapp.MainActivity
import com.example.thesparkinstituteapp.R

class Explore_Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore, container, false)


        return view
    }
}