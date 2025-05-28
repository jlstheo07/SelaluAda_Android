package com.example.selaluada.util

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceUtil {
    private const val PREF_NAME = "MyAppPreferences"
    private const val AUTH_TOKEN = "auth_token"
    private const val IS_LOGGED_IN = "is_logged_in"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveAuthToken(context: Context, token: String) {
        getPreferences(context).edit().apply {
            putString(AUTH_TOKEN, token)
            putBoolean(IS_LOGGED_IN, true)
            apply()
        }
    }

    fun getAuthToken(context: Context): String? {
        return getPreferences(context).getString(AUTH_TOKEN, null)
    }

    fun isLoggedIn(context: Context): Boolean {
        val prefs = getPreferences(context)
        val token = prefs.getString(AUTH_TOKEN, null)
        val isLogin =  !token.isNullOrEmpty() && prefs.getBoolean(IS_LOGGED_IN, true)
        return isLogin
    }

    fun clearAuthToken(context: Context) {
        getPreferences(context).edit().apply {
            remove(AUTH_TOKEN)
            putBoolean(IS_LOGGED_IN, false)
            apply()
        }
    }
}
