package com.example.bloom

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bloom.screens.advanced_info.AdvancedInformationViewModel
import com.example.bloom.screens.auth.LoginViewModel
import com.example.bloom.screens.auth.VerificationViewModel
import com.example.bloom.screens.basic_information.BasicInformationViewModel
import com.example.bloom.screens.connection.ChatViewModel
import com.example.bloom.screens.connection.ConnectionListViewModel
import com.example.bloom.screens.home.ProfileViewModel
import com.example.bloom.screens.home.HomeScreenViewModel
import com.example.bloom.screens.information.InformationViewModel
import com.example.bloom.screens.settings.SettingsViewModel

object AppViewModelProvider {
    val factory = viewModelFactory {
        initializer {
            NavigationViewModel(myApp().userPreference)
        }

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
            BasicInformationViewModel(myApp().userPreference)
        }
        initializer {
            InformationViewModel(myApp().userPreference)
        }
        initializer {
            HomeScreenViewModel(myApp().userPreference, myApp().applicationContext)
        }
        initializer {
            ProfileViewModel(myApp().userPreference)
        }
        initializer {
            SettingsViewModel(myApp().userPreference)
        }
        initializer {
            VerificationViewModel(myApp().userPreference,this.createSavedStateHandle())
        }
        initializer {
            AdvancedInformationViewModel(myApp().userPreference,myApp().applicationContext)
        }
    }
}

fun CreationExtras.myApp(): BloomApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BloomApplication)