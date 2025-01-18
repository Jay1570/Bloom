package com.example.bloom.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChangeEmailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChangeEmailUiState())
    val uiState = _uiState.asStateFlow()

    private val isPasswordVisible: Boolean get() = _uiState.value.isPasswordVisible

    fun onEmailChange(newValue: String) {
        _uiState.update {
            it.copy(email = newValue)
        }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update {
            it.copy(password = newValue)
        }
    }

    fun onPasswordVisibilityChange() {
        _uiState.value = _uiState.value.copy(isPasswordVisible = ! isPasswordVisible)
    }

    fun onConfirmClick(navigateTo: () -> Unit) {
        _uiState.value = _uiState.value.copy(inProcess = true)
        viewModelScope.launch {

        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(inProcess = false)
            navigateTo()
        }
    }
}

data class ChangeEmailUiState(
    val email: String = "",
    val password: String = "",
    val inProcess: Boolean = false,
    val isPasswordVisible: Boolean = false
)