package com.example.thesparkinstituteapp.Navodaya

import android.R.attr.phoneNumber
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
import com.google.android.play.integrity.internal.u

class Class_9 : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_class_9, container, false)

        val whatsappButton = view.findViewById<LinearLayout>(R.id.whatsappButtonClass9Navodaya)
        whatsappButton.setOnClickListener {
            openWhatsApp()
        }


    return view
    }

    private fun openWhatsApp() {
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