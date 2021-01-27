package com.amannegi.notes.utils

import android.content.Context

class SessionManager(context: Context) {
    // logged in user session management
    private val KEY_LOGGED_IN = "mLoggedIn"
    private val KEY_NAME = "mName"
    private val KEY_EMAIL = "mEmail"

    private val sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun clearPrefs() {
        editor.clear().apply()
        editor.putBoolean(KEY_LOGGED_IN, false)
    }

    fun getLoginStatus() = sharedPreferences.getBoolean(KEY_LOGGED_IN, false)

    fun setData(name: String?, email: String?) {
        editor.putBoolean(KEY_LOGGED_IN, true)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_EMAIL, email)
        editor.apply()
    }

    fun getName() = sharedPreferences.getString(KEY_NAME, "Null")
    fun getEmail() = sharedPreferences.getString(KEY_EMAIL, "Null")

}