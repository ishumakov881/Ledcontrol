package com.cyberlights.ledcontrol.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.cyberlights.ledcontrol.AndroidApp

actual fun copyToClipboard(text: String) {
    val clipboard = AndroidApp.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Manufacturer Data", text)
    clipboard.setPrimaryClip(clip)
} 