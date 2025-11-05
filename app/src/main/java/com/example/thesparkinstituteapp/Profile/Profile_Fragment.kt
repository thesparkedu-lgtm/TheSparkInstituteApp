package com.example.thesparkinstituteapp.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.thesparkinstituteapp.R
import com.example.thesparkinstituteapp.loginandregister.LoginFragment
import com.example.thesparkinstituteapp.sharedPre.SharedPrefHelper
import com.google.firebase.auth.FirebaseAuth

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

            auth.signOut()
            sharedPrefHelper.saveLoginState(false)


            val fm = requireActivity().supportFragmentManager
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            val transaction = fm.beginTransaction()
            transaction.replace(R.id.fragment_container, LoginFragment())
            transaction.commit()
        }

        return view
    }
}
