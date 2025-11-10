package com.example.thesparkinstituteapp.Profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.thesparkinstituteapp.R
import com.example.thesparkinstituteapp.loginandregister.Login_Register_Activity
import com.example.thesparkinstituteapp.sharedPre.SharedPrefHelper
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class ProfileFragment : Fragment() {

    private lateinit var ivProfilePicture: ImageView
    private lateinit var tvProfileName: TextView
    private lateinit var tvProfileEmail: TextView
    private lateinit var btnEditProfile: Button
    private lateinit var cvMenuIcon: View
    private lateinit var ivMenu: ImageView

    private lateinit var sharedPrefHelper: SharedPrefHelper
    private lateinit var auth: FirebaseAuth

    private var selectedImageUri: Uri? = null

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                try {
                    requireContext().contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    selectedImageUri = uri
                    ivProfilePicture.setImageURI(uri)
                    Toast.makeText(requireContext(), "Profile picture updated!", Toast.LENGTH_SHORT).show()
                    saveToPreferences(tvProfileName.text.toString(), tvProfileEmail.text.toString())
                } catch (e: SecurityException) {
                    Toast.makeText(requireContext(), "Could not save image permanently", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error loading image", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefHelper = SharedPrefHelper(requireContext())
        auth = FirebaseAuth.getInstance()

        initViews(view)

        loadUserData()

        setupClickListeners()
    }

    private fun initViews(view: View) {
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture)
        tvProfileName = view.findViewById(R.id.tvProfileName)
        tvProfileEmail = view.findViewById(R.id.tvProfileEmail)
        btnEditProfile = view.findViewById(R.id.btnEditProfile)
        cvMenuIcon = view.findViewById(R.id.cvMenuIcon)
        ivMenu = view.findViewById(R.id.ivMenu)
    }

    private fun setupClickListeners() {
        // Edit Profile Button
        btnEditProfile.setOnClickListener {
            showEditProfileDialog()
        }

        // Profile Picture Click - Change Picture
        ivProfilePicture.setOnClickListener {
            showImagePickerDialog()
        }

        // Menu Icon Click - Show options menu
        cvMenuIcon.setOnClickListener {
            showMenuOptions()
        }

        ivMenu.setOnClickListener {
            showMenuOptions()
        }
    }

    private fun showMenuOptions() {
        val options = arrayOf("Add Course", "Logout")

        AlertDialog.Builder(requireContext())
            .setTitle("Menu")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> showAddCourseDialog()
                    1 -> showLogoutDialog()
                }
                dialog.dismiss()
            }
            .show()
    }

    private fun showAddCourseDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_course, null)
        val etCourseName = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etCourseName)
        val etDuration = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etDuration)

        AlertDialog.Builder(requireContext())
            .setTitle("Add New Course")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, _ ->
                val courseName = etCourseName.text.toString().trim()
                val durationDays = etDuration.text.toString().trim().toIntOrNull()

                if (courseName.isNotEmpty() && durationDays != null && durationDays > 0) {
                    addCourseCard(courseName, durationDays)
                    saveCourseToPrefs(courseName, durationDays)
                    dialog.dismiss()
                } else {
                    Toast.makeText(requireContext(), "Enter valid details", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    private fun loadUserData() {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid

            val firebaseName = currentUser.displayName
            val firebaseEmail = currentUser.email
            val firebasePhotoUri = currentUser.photoUrl

            tvProfileName.text = firebaseName ?: "User"
            tvProfileEmail.text = firebaseEmail ?: "email@example.com"

            val sharedPref = requireActivity().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
            val savedImageUri = sharedPref.getString("profile_image_uri_$userId", null)

            if (savedImageUri != null) {
                try {
                    selectedImageUri = Uri.parse(savedImageUri)
                    ivProfilePicture.setImageURI(selectedImageUri)
                } catch (e: Exception) {
                    e.printStackTrace()
                    firebasePhotoUri?.let {
                        try {
                            ivProfilePicture.setImageURI(it)
                            selectedImageUri = it
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }
                }
            } else {
                firebasePhotoUri?.let {
                    try {
                        ivProfilePicture.setImageURI(it)
                        selectedImageUri = it
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            saveToPreferences(
                tvProfileName.text.toString(),
                tvProfileEmail.text.toString()
            )
        } else {
            loadFromPreferences()
        }
    }

    private fun showEditProfileDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_profile, null)
        val etName = dialogView.findViewById<TextInputEditText>(R.id.etName)
        val etEmail = dialogView.findViewById<TextInputEditText>(R.id.etEmail)

        etName.setText(tvProfileName.text.toString())
        etEmail.setText(tvProfileEmail.text.toString())

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Profile")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                val newName = etName.text.toString().trim()
                val newEmail = etEmail.text.toString().trim()

                if (newName.isNotEmpty() && newEmail.isNotEmpty()) {
                    updateProfile(newName, newEmail)
                    dialog.dismiss()
                } else {
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("Choose from Gallery", "Cancel")

        AlertDialog.Builder(requireContext())
            .setTitle("Change Profile Picture")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> openGallery()
                    1 -> dialog.dismiss()
                }
            }
            .show()
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog, _ ->
                performLogout()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun performLogout() {
        clearProfileData()

        auth.signOut()

        sharedPrefHelper.saveLoginState(false)

        val intent = Intent(requireContext(), Login_Register_Activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        requireActivity().finish()

        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
    }

    private fun clearProfileData() {
        val sharedPref = requireActivity().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            clear()
            apply()
        }

        selectedImageUri = null
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }
        pickImageLauncher.launch(intent)
    }

    private fun updateProfile(name: String, email: String) {
        // Update UI
        tvProfileName.text = name
        tvProfileEmail.text = email

        // Update Firebase user profile
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()

            currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Failed to update Firebase profile", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        saveToPreferences(name, email)
    }

    private fun saveToPreferences(name: String, email: String) {
        val userId = auth.currentUser?.uid ?: "default"

        val sharedPref = requireActivity().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("profile_name_$userId", name)
            putString("profile_email_$userId", email)
            if (selectedImageUri != null) {
                putString("profile_image_uri_$userId", selectedImageUri.toString())
            }
            apply()
        }
    }

    private fun loadFromPreferences() {
        try {
            // Get current user ID to load user-specific data
            val userId = auth.currentUser?.uid ?: "default"

            val sharedPref = requireActivity().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
            val savedName = sharedPref.getString("profile_name_$userId", "User")
            val savedEmail = sharedPref.getString("profile_email_$userId", "email@example.com")
            val savedImageUri = sharedPref.getString("profile_image_uri_$userId", null)

            tvProfileName.text = savedName ?: "User"
            tvProfileEmail.text = savedEmail ?: "email@example.com"

            savedImageUri?.let {
                try {
                    selectedImageUri = Uri.parse(it)
                    ivProfilePicture.setImageURI(selectedImageUri)
                } catch (e: Exception) {
                    e.printStackTrace()
                    clearSavedImageUri()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            tvProfileName.text = "User"
            tvProfileEmail.text = "email@example.com"
        }
    }

    private fun clearSavedImageUri() {
        val userId = auth.currentUser?.uid ?: "default"

        val sharedPref = requireActivity().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("profile_image_uri_$userId")
            apply()
        }
        selectedImageUri = null
    }
    override fun onResume() {
        super.onResume()
        loadUserData()
        loadCourses()
    }

    private fun addCourseCard(courseName: String, durationDays: Int) {
        val layout = view?.findViewById<LinearLayout>(R.id.courseListLayout) ?: return

        val card = layoutInflater.inflate(R.layout.item_course_card, layout, false)
        val tvCourseName = card.findViewById<TextView>(R.id.tvCourseName)
        val tvProgressText = card.findViewById<TextView>(R.id.tvProgressText)
        val progressBar = card.findViewById<ProgressBar>(R.id.progressBar)
        val btnRemove = card.findViewById<ImageView>(R.id.btnRemove)

        tvCourseName.text = courseName

        val startDate = getSavedStartDate(courseName) ?: System.currentTimeMillis()
        val progress = calculateProgress(startDate, durationDays)

        tvProgressText.text = "$progress% Completed"
        progressBar.progress = progress

        btnRemove.setOnClickListener {
            removeCourse(courseName, card)
        }

        layout.addView(card)
    }

    private fun calculateProgress(startDate: Long, durationDays: Int): Int {
        val current = System.currentTimeMillis()
        val diffDays = ((current - startDate) / (1000 * 60 * 60 * 24)).toInt()
        val progressEvery4Days = (diffDays / 4f) / (durationDays / 4f) * 100
        return progressEvery4Days.coerceIn(0f, 100f).toInt()
    }

    private fun saveCourseToPrefs(courseName: String, durationDays: Int) {
        val userId = auth.currentUser?.uid ?: return
        val prefs = requireContext().getSharedPreferences("CoursesPrefs_$userId", Context.MODE_PRIVATE)
        val startDate = System.currentTimeMillis()
        with(prefs.edit()) {
            putLong("${courseName}_start", startDate)
            putInt("${courseName}_duration", durationDays)
            apply()
        }
        loadCourses()
    }

    private fun getSavedStartDate(courseName: String): Long? {
        val prefs = requireContext().getSharedPreferences("CoursesPrefs", Context.MODE_PRIVATE)
        val date = prefs.getLong("${courseName}_start", -1L)
        return if (date == -1L) null else date
    }

    private fun removeCourse(courseName: String, view: View) {
        val userId = auth.currentUser?.uid ?: return
        val prefs = requireContext().getSharedPreferences("CoursesPrefs_$userId", Context.MODE_PRIVATE)
        with(prefs.edit()) {
            remove("${courseName}_start")
            remove("${courseName}_duration")
            apply()
        }
        (view.parent as? ViewGroup)?.removeView(view)
    }

    private fun loadCourses() {
        val userId = auth.currentUser?.uid ?: return
        val prefs = requireContext().getSharedPreferences("CoursesPrefs_$userId", Context.MODE_PRIVATE)
        val allEntries = prefs.all
        val layout = view?.findViewById<LinearLayout>(R.id.courseListLayout) ?: return
        layout.removeAllViews()

        allEntries.keys
            .filter { it.endsWith("_duration") }
            .forEach {
                val courseName = it.replace("_duration", "")
                val duration = prefs.getInt(it, 0)
                addCourseCard(courseName, duration)
            }
    }


    companion object {
        fun newInstance() = ProfileFragment()
    }
}