package com.example.thesparkinstituteapp.Profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.thesparkinstituteapp.R
import com.example.thesparkinstituteapp.loginandregister.Login_Register_Activity
import com.example.thesparkinstituteapp.sharedPre.SharedPrefHelper
import com.google.firebase.auth.FirebaseAuth
import kotlin.jvm.java

class ProfileFragment : Fragment() {

    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        sharedPrefHelper = SharedPrefHelper(requireContext())
        auth = FirebaseAuth.getInstance()

        val logoutBtn = view.findViewById<Button>(R.id.btnLogout)

        logoutBtn.setOnClickListener {
            // Sign out user from Firebase
            auth.signOut()

            // Update login state in SharedPreferences
            sharedPrefHelper.saveLoginState(false)

            // Move to LoginActivity
            val intent = Intent(requireContext(), Login_Register_Activity::class.java)
            startActivity(intent)

            // Finish MainActivity so user can't go back with back button
            requireActivity().finish()
        }

        return view
    }
}
