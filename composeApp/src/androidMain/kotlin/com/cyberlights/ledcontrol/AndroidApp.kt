package com.cyberlights.ledcontrol

import android.app.Application
import android.content.Context

class AndroidApp : Application() {


    override fun onCreate() {
        super.onCreate()

    }


    companion object {
        lateinit var applicationContext: Context
    }
} 