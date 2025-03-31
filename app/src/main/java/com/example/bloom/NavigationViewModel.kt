package com.example.bloom

import androidx.lifecycle.ViewModel

class NavigationViewModel(private val userPreference: UserPreference) : ViewModel() {
    fun startDestination(): Routes {
        return if (userPreference.user.value.isEmpty()) {
            Auth
        } else {
            Home
        }
    }
}