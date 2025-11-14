package com.example.thesparkinstituteapp.Courses_Classes.Coaching

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.thesparkinstituteapp.R

class Coaching_Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_coaching, container, false)

        val class12 = view.findViewById<LinearLayout>(R.id.class12Btn)
        val  englishFocus =view.findViewById<LinearLayout>(R.id.englishFocusBTn)
        val science = view.findViewById<LinearLayout>(R.id.scienceBTn)
        val math = view.findViewById<LinearLayout>(R.id.mathBtn)
        val science11_12 = view.findViewById<LinearLayout>(R.id.science11_12Btn)

        class12.setOnClickListener {
            openFragment(Class12())
        }

        englishFocus.setOnClickListener {
            openFragment(English_Focus())
        }

        science.setOnClickListener {
            openFragment(Science())
        }

        math.setOnClickListener {
            openFragment(Math())
        }

        science11_12.setOnClickListener {
            openFragment(Science11_12())
        }


        return view
    }
    fun openFragment(frag1: Fragment){
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.fade_slide_in_left,
            R.anim.fade_slide_out_left,
            R.anim.fade_slide_in_right,
            R.anim.fade_slide_out_right

        )
        transaction.replace(R.id.fragment_container_2, frag1)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}