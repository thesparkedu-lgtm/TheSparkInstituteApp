package com.example.thesparkinstituteapp.Courses_Classes.Novodaya

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.SurfaceControl
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.thesparkinstituteapp.R

class Navodaya_Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       val view = inflater.inflate(R.layout.fragment_navodaya, container, false)

        val class6 = view.findViewById<LinearLayout>(R.id.Class6)
        val class9 = view.findViewById<LinearLayout>(R.id.Class9)
        val class11 = view.findViewById<LinearLayout>(R.id.Class11)


        class6.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_2, Class_6())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        class9.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.fade_slide_in_left,
                R.anim.fade_slide_out_left,
                R.anim.fade_slide_in_right,
                R.anim.fade_slide_out_right

            )
            transaction.replace(R.id.fragment_container_2, Class_9())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        class11.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.fade_slide_in_left,
                R.anim.fade_slide_out_left,
                R.anim.fade_slide_in_right,
                R.anim.fade_slide_out_right

            )
            transaction.replace(R.id.fragment_container_2, Class_11())
            transaction.addToBackStack(null)
            transaction.commit()
        }

     return view

    }


}