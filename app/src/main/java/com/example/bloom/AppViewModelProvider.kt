package com.example.bloom

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bloom.screens.connection.ChatViewModel
import com.example.bloom.screens.connection.HomeViewModel

object AppViewModelProvider {
    val factory = viewModelFactory {


        initializer {
            HomeViewModel(myApp().userPreference)
        }

        initializer {
            ChatViewModel(this.createSavedStateHandle(), myApp().userPreference)
        }
    }
}

fun CreationExtras.myApp(): BloomApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloomApplication)