package com.oceanx.freshbasket.utils

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    private const val PREF_NAME = "session_prefs"
    private const val KEY_LOGGED_IN = "is_logged_in"
    private const val KEY_PHONE = "phone_number"

    fun isLoggedIn(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_LOGGED_IN, false)
    }

    fun saveLogin(context: Context, phone: String) {
        getPrefs(context).edit()
            .putBoolean(KEY_LOGGED_IN, true)
            .putString(KEY_PHONE, phone)
            .apply()
    }

    fun getPhone(context: Context): String {
        return getPrefs(context).getString(KEY_PHONE, "") ?: ""
    }

    fun logout(context: Context) {
        getPrefs(context).edit().clear().apply()
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}
