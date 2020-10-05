package com.example.animals.util

import android.content.Context
import android.preference.PreferenceManager

class SharedPrefHelper(context: Context) {
    private val prefApiKey = "api_key"
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun saveApiKey(key: String) {
        prefs.edit().putString(prefApiKey, key).apply()
    }

    fun getApiKey() = prefs.getString(prefApiKey, null)
}