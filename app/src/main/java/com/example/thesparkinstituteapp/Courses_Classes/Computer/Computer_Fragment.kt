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
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad","SpokenEnglish")
            startActivity(intent)
        }

        spokenEng_Comp.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad","SpokenEnglish_BasicComputer")
            startActivity(intent)
        }

        python.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad","Python")
            startActivity(intent)
        }

        ADCA.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad","ADCA")
            startActivity(intent)
        }

        DCA.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad","DCA")
            startActivity(intent)
        }

        Java.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad","Java")
            startActivity(intent)
        }

        Kotlin.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad","Kotlin")
            startActivity(intent)
        }

        Android_Development.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad","Android_Development")
            startActivity(intent)
        }

        VideoEditing.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad","VideoEditing")
            startActivity(intent)
        }

       Graphic.setOnClickListener {
           val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
           intent.putExtra("fragmentToLoad","Graphic")
           startActivity(intent)
       }

        webDesign.setOnClickListener {
            val intent = Intent(requireContext(), Courses_Classes_Activity::class.java)
            intent.putExtra("fragmentToLoad","webDesign")


        }




        return view
    }


}