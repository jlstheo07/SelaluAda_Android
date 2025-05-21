package com.example.selaluada.util

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceUtil {
    private const val PREF_NAME = "MyAppPreferences"
    private const val AUTH_TOKEN = "auth_token"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveAuthToken(context: Context, token: String) {
        val editor = getPreferences(context).edit()
        editor.putString(AUTH_TOKEN, token)
        editor.apply()
    }

    fun getAuthToken(context: Context): String? {
        return getPreferences(context).getString(AUTH_TOKEN, null)
    }

    fun clearAuthToken(context: Context) {
        val editor = getPreferences(context).edit()
        editor.remove(AUTH_TOKEN)
        editor.apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        val authToken = getAuthToken(context)
        return !authToken.isNullOrEmpty()
    }
}

