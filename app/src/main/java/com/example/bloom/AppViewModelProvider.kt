package com.example.bloom

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bloom.screens.auth.LoginViewModel
import com.example.bloom.screens.auth.VerificationViewModel
import com.example.bloom.screens.connection.ChatViewModel
import com.example.bloom.screens.connection.ConnectionListViewModel

object AppViewModelProvider {
    val factory = viewModelFactory {


        initializer {
            ConnectionListViewModel(myApp().userPreference)
        }

        initializer {
            ChatViewModel(this.createSavedStateHandle(), myApp().userPreference)
        }
        initializer {
            LoginViewModel(myApp().userPreference)
        }
        initializer {
            VerificationViewModel(this.createSavedStateHandle())
        }
    }
}

fun CreationExtras.myApp(): BloomApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloomApplication)