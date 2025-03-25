package com.example.bloom

import android.app.Application

class BloomApplication : Application() {

    companion object {
        lateinit var themePreference: ThemePreference

    }

    lateinit var userPreference: UserPreference
    override fun onCreate() {
        super.onCreate()
        themePreference = ThemePreference(this)
        userPreference = UserPreference(this)
    }

}