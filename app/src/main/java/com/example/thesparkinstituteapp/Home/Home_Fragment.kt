package com.example.thesparkinstituteapp.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.thesparkinstituteapp.Coaching.Coaching_Fragment
import com.example.thesparkinstituteapp.Competative.Competative_Fragment
import com.example.thesparkinstituteapp.Computer.Computer_Fragment
import com.example.thesparkinstituteapp.Navodaya.Navodaya_Fragment
import com.example.thesparkinstituteapp.Profile.ProfileFragment
import com.example.thesparkinstituteapp.R
import com.example.thesparkinstituteapp.sharedPre.SharedPrefHelper

class HomeFragment : Fragment() {

    private lateinit var prefs: SharedPrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val profileIcon = view.findViewById<ImageView>(R.id.profile)
        val navodayaBtn = view.findViewById<LinearLayout>(R.id.NavodayaBtn)
        val competativaBtn = view.findViewById<LinearLayout>(R.id.CompetitiveBtn)
        val computerBtn = view.findViewById<LinearLayout>(R.id.ComputerBtn)
        val coachingBtn = view.findViewById<LinearLayout>(R.id.CoachingBtn)

        profileIcon.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.fade_slide_in_left,
                R.anim.fade_slide_out_left,
                R.anim.fade_slide_in_right,
                R.anim.fade_slide_out_right

            )
            transaction.replace(R.id.fragment_container, ProfileFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        navodayaBtn.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.fade_slide_in_left,
                R.anim.fade_slide_out_left,
                R.anim.fade_slide_in_right,
                R.anim.fade_slide_out_right

            )
            transaction.replace(R.id.fragment_container, Navodaya_Fragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        competativaBtn.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.fade_slide_in_left,
                R.anim.fade_slide_out_left,
                R.anim.fade_slide_in_right,
                R.anim.fade_slide_out_right

            )
            transaction.replace(R.id.fragment_container, Competative_Fragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        computerBtn.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.fade_slide_in_left,
                R.anim.fade_slide_out_left,
                R.anim.fade_slide_in_right,
                R.anim.fade_slide_out_right

            )
            transaction.replace(R.id.fragment_container, Computer_Fragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        coachingBtn.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.fade_slide_in_left,
                R.anim.fade_slide_out_left,
                R.anim.fade_slide_in_right,
                R.anim.fade_slide_out_right

            )
            transaction.replace(R.id.fragment_container, Coaching_Fragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }
}
