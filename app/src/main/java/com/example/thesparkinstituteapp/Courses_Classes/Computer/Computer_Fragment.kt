package com.example.thesparkinstituteapp.Courses_Classes.Computer

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.thesparkinstituteapp.Courses_Classes.Courses_Classes_Activity
import com.example.thesparkinstituteapp.R
import com.example.thesparkinstituteapp.Video_Classes.VideoFragment


class Computer_Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_computer, container, false)

        val ADCA = view.findViewById<LinearLayout>(R.id.ADCA_From_Computer_Fragment)
        val DCA = view.findViewById<LinearLayout>(R.id.DCA_From_Computer_Fragment)
        val Java = view.findViewById<LinearLayout>(R.id.Java_From_Computer_Fragment)
        val Kotlin = view.findViewById<LinearLayout>(R.id.Kotlin_From_Computer_Fragment)
        val Android_Development = view.findViewById<LinearLayout>(R.id.Android_Development_From_Computer_Fragment)
        val VideoEditing = view.findViewById<LinearLayout>(R.id.Video_From_Computer_Fragment)
        val webDesign = view.findViewById<LinearLayout>(R.id.Web_Development_From_Computer_Fragment)
        val Graphic = view.findViewById<LinearLayout>(R.id.Graphics_From_Computer_Fragment)
        val python = view.findViewById<LinearLayout>(R.id.Python_From_Computer_Fragment)
        val spokenEnglish = view.findViewById<LinearLayout>(R.id.SpokenEngBtn)
        val spokenEng_Comp = view.findViewById<LinearLayout>(R.id.SpokenEng_CompBtn)

        spokenEnglish.setOnClickListener {
            openFragment(SpokenEnglish())
        }

        spokenEng_Comp.setOnClickListener {
           openFragment(SpokenEnglish_BasicComputer())
        }

        python.setOnClickListener {
            openFragment(Python_Fragment())
        }

        ADCA.setOnClickListener {
           openFragment(ADCA_Fragment())
        }

        DCA.setOnClickListener {
            openFragment(DCA_Fragment())
        }

        Java.setOnClickListener {
           openFragment(Java_Fragment())
        }

        Kotlin.setOnClickListener {
           openFragment(Kotlin_Fragment())
        }

        Android_Development.setOnClickListener {
            openFragment(Android_Development())
        }

        VideoEditing.setOnClickListener {
           openFragment(VideoFragment())
        }

       Graphic.setOnClickListener {
           openFragment(Graphic_Fragment())
       }

        webDesign.setOnClickListener {
           openFragment(Web_design())
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