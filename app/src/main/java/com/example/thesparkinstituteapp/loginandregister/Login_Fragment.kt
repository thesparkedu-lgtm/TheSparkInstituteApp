package com.example.thesparkinstituteapp.loginandregister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.thesparkinstituteapp.Home.HomeFragment
import com.example.thesparkinstituteapp.R
import com.example.thesparkinstituteapp.loginandregister.RegisterFragment
import com.example.thesparkinstituteapp.sharedPre.SharedPrefHelper
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()
        sharedPrefHelper = SharedPrefHelper(requireContext())

        val emailEt = view.findViewById<EditText>(R.id.login_Email)
        val passwordEt = view.findViewById<EditText>(R.id.login_Password)
        val loginBtn = view.findViewById<Button>(R.id.button_login)
        val signupBtn = view.findViewById<TextView>(R.id.go_Signup)

        loginBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sharedPrefHelper.saveLoginState(true)
                        Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()

                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, HomeFragment())
                            .commit()
                    } else {
                        Toast.makeText(requireContext(), "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        signupBtn.setOnClickListener {
            Toast.makeText(requireContext(), "Opening Register", Toast.LENGTH_SHORT).show()

            // Prevent crash when fragment already destroyed
            if (isAdded && activity != null) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, RegisterFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }

        return view
    }
}
