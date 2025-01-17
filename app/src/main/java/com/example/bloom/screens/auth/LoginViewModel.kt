package com.example.bloom.screens.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var uiState = mutableStateOf(LoginUiState())
        private set

    private val username get() = uiState.value.username
    private val password get() = uiState.value.password
    private val isPasswordVisible get() = uiState.value.isPasswordVisible

    fun onUsernameChange(newValue: String) {
        uiState.value = uiState.value.copy(username = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onVisibilityChange() {
        uiState.value = uiState.value.copy(isPasswordVisible = ! isPasswordVisible)
    }

    fun onLoginClick(navigateTo: () -> Unit) {
        uiState.value = uiState.value.copy(inProcess = true)
        viewModelScope.launch {
            delay(2000)
        }.invokeOnCompletion {
            uiState.value = uiState.value.copy(inProcess = false)
            navigateTo()
        }
    }
}

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val inProcess: Boolean = false
)