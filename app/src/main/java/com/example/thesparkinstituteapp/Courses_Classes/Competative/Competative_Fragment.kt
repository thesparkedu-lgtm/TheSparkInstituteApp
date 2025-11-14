package com.example.thesparkinstituteapp.Courses_Classes.Competative


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
//import com.example.thesparkinstituteapp.Courses_Classes.Novodaya.Class_9
import com.example.thesparkinstituteapp.R


class Competative_Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_competative, container, false)
        val agniveerBtn = view.findViewById<LinearLayout>(R.id.agniveerBtn)
        val ssc_gdBtn = view.findViewById<LinearLayout>(R.id.SSC_GD)
        val wbPoliceBtn = view.findViewById<LinearLayout>(R.id.WB_Pollice)
        val railwayBtn = view.findViewById<LinearLayout>(R.id.Railway)
        val indianArmy = view.findViewById<LinearLayout>(R.id.Indian_army)

        agniveerBtn.setOnClickListener {
            openFragment(Agniveer())
        }

        ssc_gdBtn.setOnClickListener {
            openFragment(SSC_GD())
        }

        wbPoliceBtn.setOnClickListener {
            openFragment(WB_Police())
        }

        railwayBtn.setOnClickListener {
            openFragment(Railway())
        }

        indianArmy.setOnClickListener {
            openFragment(Indian_Army())
        }

        return view
    }
       fun openFragment(frag: Fragment){
           val transaction = requireActivity().supportFragmentManager.beginTransaction()
           transaction.setCustomAnimations(
               R.anim.fade_slide_in_left,
               R.anim.fade_slide_out_left,
               R.anim.fade_slide_in_right,
               R.anim.fade_slide_out_right

           )
           transaction.replace(R.id.fragment_container_2, frag)
           transaction.addToBackStack(null)
           transaction.commit()
       }

}