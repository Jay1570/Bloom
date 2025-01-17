package com.example.bloom.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerificationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(VerificationUiState())
    val uiState = _uiState.asStateFlow()

    fun onCodeChange(newValue: String) {
        _uiState.update {
            it.copy(code = newValue)
        }
    }

    fun verifyEmail(navigateTo: () -> Unit) {
        _uiState.value = _uiState.value.copy(inProcess = true)
        viewModelScope.launch {
            //delay(2000)
        }.invokeOnCompletion {
            _uiState.value = _uiState.value.copy(inProcess = false)
            navigateTo()
        }
    }
}

data class VerificationUiState(
    val code: String = "",
    val inProcess: Boolean = false
)