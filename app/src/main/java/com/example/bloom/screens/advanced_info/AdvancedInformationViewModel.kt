package com.example.bloom.screens.advanced_info

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.SnackbarEvent
import com.example.bloom.SnackbarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdvancedInformationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AdvancedInformationUiState())
    val uiState get() = _uiState.asStateFlow()

    private val currentTab: Int get() = _uiState.value.currentTab

    fun goToPrevious() {
        _uiState.update { it.copy(currentTab = currentTab - 1) }
    }

    fun goToNext() {
        when (currentTab) {
            0 -> if (_uiState.value.images.size == 6) incrementCurrentTab() else showSnackbar("Please select at least 6 images")
        }
    }

    fun addImage(index: Int, imageUri: Uri) {
        val updatedImages = _uiState.value.images.toMutableList().apply {
            this[index] = imageUri
        }
        _uiState.update { it.copy(images = updatedImages) }
    }

    fun removeImage(index: Int) {
        val updatedImages = _uiState.value.images.toMutableList().apply {
            this[index] = Uri.EMPTY
        }
        _uiState.update { it.copy(images = updatedImages) }
    }

    private fun incrementCurrentTab() {
        _uiState.update { it.copy(currentTab = currentTab + 1) }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            SnackbarManager.sendEvent(SnackbarEvent(message))
        }
    }
}

data class AdvancedInformationUiState(
    val currentTab: Int = 0,
    val images: List<Uri> = List(6) { Uri.EMPTY }
)