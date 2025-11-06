package com.example.thesparkinstituteapp.sharedPre

import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("SparkPrefs", Context.MODE_PRIVATE)

    fun saveLoginState(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("isLoggedIn", false)
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
