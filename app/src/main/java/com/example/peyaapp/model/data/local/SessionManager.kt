package com.example.peyaapp.model.data.local

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun setLoggedInUser() {
        prefs.edit()
            .putBoolean("is_logged_in", true)
            .apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("is_logged_in", false)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}