package com.cyberlights.ledcontrol.data.settings

expect class SettingsStorage() {
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun putBoolean(key: String, value: Boolean)
} 