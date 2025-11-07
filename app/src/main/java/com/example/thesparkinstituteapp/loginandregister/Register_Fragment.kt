package com.example.thesparkinstituteapp.loginandregister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.thesparkinstituteapp.MainActivity
import com.example.thesparkinstituteapp.R
import com.example.thesparkinstituteapp.sharedPre.SharedPrefHelper
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var prefs: SharedPrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        auth = FirebaseAuth.getInstance()
        prefs = SharedPrefHelper(requireContext())

        val emailEt = view.findViewById<EditText>(R.id.email)
        val passEt = view.findViewById<EditText>(R.id.confirmPassword)
        val registerBtn = view.findViewById<Button>(R.id.sign_upBtn)
        val gotoLogin = view.findViewById<TextView>(R.id.Login) // “Already have account? Login”

        registerBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val pass = passEt.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        prefs.saveLoginState(true)
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    } else {
                        Toast.makeText(requireContext(), "Register failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        gotoLogin.setOnClickListener {
            (activity as Login_Register_Activity).replaceFragment(LoginFragment())
        }

        return view
    }
}
