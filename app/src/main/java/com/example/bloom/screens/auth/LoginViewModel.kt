package com.example.bloom.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.SnackbarEvent
import com.example.bloom.SnackbarManager
import com.example.bloom.UserPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(val userPreference: UserPreference) : ViewModel() {


    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    fun onLoginClick(navigateTo: () -> Unit) {
        if (uiState.value.username.length == 10) { // Ensure 10-digit phone number
            userPreference.setUserId(uiState.value.username)

            navigateTo()
        } else {
            showSnackbar("Please enter a valid 10-digit phone number.")
        }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            // Assuming you have a SnackbarManager in your app
            SnackbarManager.sendEvent(SnackbarEvent(message))
        }
    }
}

data class LoginUiState(
    val username: String = "",
    val inProcess: Boolean = false
)
