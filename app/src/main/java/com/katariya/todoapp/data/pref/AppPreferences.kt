package com.katariya.todoapp.data.pref

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    lateinit var preferences: SharedPreferences
    private const val PREFERENCES_FILE_NAME = "todo_pref"
    fun with(application: Application) {
        preferences = application.getSharedPreferences(
            PREFERENCES_FILE_NAME, Context.MODE_PRIVATE
        )
    }

    fun saveLoginStatus(status: Boolean) {
        preferences.edit().putBoolean("logging", status).apply()
    }

    fun getLoginStatus(): Boolean {
        return preferences.getBoolean("logging", false)
    }
}