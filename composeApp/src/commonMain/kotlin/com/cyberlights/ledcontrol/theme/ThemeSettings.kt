package com.cyberlights.ledcontrol.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.cyberlights.ledcontrol.data.settings.SettingsStorage

object ThemeSettings {
    private val storage = SettingsStorage()
    private val _isDarkTheme = mutableStateOf(storage.getBoolean("dark_theme", false))
    var isDarkTheme: Boolean
        get() = _isDarkTheme.value
        set(value) {
            _isDarkTheme.value = value
            storage.putBoolean("dark_theme", value)
        }
} 