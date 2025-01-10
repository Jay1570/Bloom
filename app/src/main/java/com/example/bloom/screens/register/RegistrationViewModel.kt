package com.example.bloom.screens.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    var uiState = mutableStateOf(RegistrationUiState())
        private set

    private val email get() = uiState.value.email
    private val username get() = uiState.value.username
    private val password get() = uiState.value.password
    private val confirmPassword get() = uiState.value.confirmPassword
    private val isPasswordVisible get() = uiState.value.isPasswordVisible

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onUsernameChange(newValue: String) {
        uiState.value = uiState.value.copy(username = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onConfirmPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(confirmPassword = newValue)
    }

    fun onVisibilityChange() {
        uiState.value = uiState.value.copy(isPasswordVisible = ! isPasswordVisible)
    }

    fun onRegisterClick(navigateTo: () -> Unit) {
        uiState.value = uiState.value.copy(inProcess = true)
        viewModelScope.launch {
            delay(2000)
        }.invokeOnCompletion {
            uiState.value = uiState.value.copy(inProcess = false)
            navigateTo()
        }
    }
}

data class RegistrationUiState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val inProcess: Boolean = false
)