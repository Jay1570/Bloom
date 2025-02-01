package com.example.bloom.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState = _uiState.asStateFlow()

    private val email get() = uiState.value.email
    private val username get() = uiState.value.username
    private val password get() = uiState.value.password
    private val confirmPassword get() = uiState.value.confirmPassword
    private val isPasswordVisible get() = uiState.value.isPasswordVisible

    fun onEmailChange(newValue: String) {
        _uiState.value = _uiState.value.copy(email = newValue)
    }

    fun onUsernameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(username = newValue)
    }

    fun onPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(password = newValue)
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = newValue)
    }

    fun onVisibilityChange() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = !isPasswordVisible)
    }

    fun onRegisterClick(navigateTo: () -> Unit) {
        _uiState.value = _uiState.value.copy(inProcess = true)
        viewModelScope.launch {
            delay(2000)
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(inProcess = false)
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