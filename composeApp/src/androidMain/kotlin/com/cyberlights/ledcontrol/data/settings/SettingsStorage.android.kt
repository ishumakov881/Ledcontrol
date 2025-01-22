package com.cyberlights.ledcontrol.data.settings

import android.content.Context
import android.content.SharedPreferences
import com.cyberlights.ledcontrol.AndroidApp

actual class SettingsStorage {
    private val prefs: SharedPreferences = AndroidApp.applicationContext.getSharedPreferences(
        "app_settings",
        Context.MODE_PRIVATE
    )

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    actual fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }
} 