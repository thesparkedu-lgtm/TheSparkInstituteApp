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
import com.example.thesparkinstituteapp.sharedPre.SharedPrefHelper
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPrefHelper: SharedPrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        auth = FirebaseAuth.getInstance()
        sharedPrefHelper = SharedPrefHelper(requireContext())

        val emailRegister = view.findViewById<EditText>(R.id.email)
        val passwordRegister = view.findViewById<EditText>(R.id.confirmPassword)
        val registerBtn = view.findViewById<Button>(R.id.sign_upBtn)
        val gotoLogin = view.findViewById<TextView>(R.id.Login)

        registerBtn.setOnClickListener {
            val email = emailRegister.text.toString().trim()
            val password = passwordRegister.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        sharedPrefHelper.saveLoginState(true)
                        Toast.makeText(requireContext(), "Registered Successfully", Toast.LENGTH_SHORT).show()

                        // Go to Home Fragment
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, HomeFragment())
                            .commit()
                    } else {
                        Toast.makeText(requireContext(), "Register failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        gotoLogin.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }

        return view
    }
}
