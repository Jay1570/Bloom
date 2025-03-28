package com.example.bloom.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.BloomApplication
import com.example.bloom.Theme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    var uiState = mutableStateOf(SettingsUiState())
        private set

    private val themePreference = BloomApplication.themePreference

    init {
        viewModelScope.launch {
            themePreference.theme.collectLatest { theme ->
                uiState.value = uiState.value.copy(currentTheme = theme)
            }
        }
    }

    fun onThemeClick() {
        uiState.value = uiState.value.copy(isThemeDialogVisible = true)
    }

    fun onDismissThemeDialog() {
        uiState.value = uiState.value.copy(isThemeDialogVisible = false)
    }

    fun onThemeSelected(theme: Theme) {
        viewModelScope.launch {
            themePreference.setTheme(theme)
        }
    }

}

data class SettingsUiState(
    val isThemeDialogVisible: Boolean = false,
    val currentTheme: Theme = Theme.SYSTEM,
)