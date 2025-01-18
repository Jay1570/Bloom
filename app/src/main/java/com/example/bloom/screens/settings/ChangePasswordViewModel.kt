package com.example.bloom.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChangePasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ChangePasswordUiState())
    val uiState = _uiState.asStateFlow()

    private val isPasswordVisible: Boolean get() = _uiState.value.isPasswordVisible

    fun onOldPasswordChange(newValue: String) {
        _uiState.update {
            it.copy(oldPassword = newValue)
        }
    }

    fun onNewPasswordChange(newValue: String) {
        _uiState.update {
            it.copy(newPassword = newValue)
        }
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.update {
            it.copy(confirmPassword = newValue)
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

data class ChangePasswordUiState(
    val oldPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val inProcess: Boolean = false
)