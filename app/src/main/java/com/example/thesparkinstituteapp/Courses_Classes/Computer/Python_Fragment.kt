package com.example.thesparkinstituteapp.Courses_Classes.Computer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.example.thesparkinstituteapp.R


class Python_Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     val view = inflater.inflate(R.layout.fragment_python_, container, false)

        val whatsappButton = view.findViewById<LinearLayout>(R.id.WhatsappBtnPython)

        whatsappButton.setOnClickListener {
            openWhatsApp()
        }

        return view
    }


    fun openWhatsApp() {
        val phoneNumber = "919832116164"
        val message = "Hello! I want to know more about The Spark Institute."

        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://wa.me/$phoneNumber?text=${Uri.encode(message)}")
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "WhatsApp not installed!", Toast.LENGTH_SHORT).show()
        }
    }
}