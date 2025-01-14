package com.example.bloom

import android.app.Application

class BloomApplication : Application() {

    companion object {
        lateinit var themePreference: ThemePreference
    }

    override fun onCreate() {
        super.onCreate()
        themePreference = ThemePreference(applicationContext)
    }
}