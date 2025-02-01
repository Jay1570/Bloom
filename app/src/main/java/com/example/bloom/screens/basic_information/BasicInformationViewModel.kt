package com.example.bloom.screens.basic_information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.SnackbarEvent
import com.example.bloom.SnackbarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class BasicInformationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BasicInformationUiState())
    val uiState = _uiState.asStateFlow()

    private val isDialogVisible: Boolean get() = _uiState.value.isDialogVisible

    fun onFirstNameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(firstName = newValue)
    }

    fun onLastNameChange(newValue: String) {
        _uiState.value = _uiState.value.copy(lastName = newValue)
    }

    fun onDateChange(day: String, month: String, year: String) {
        _uiState.value = _uiState.value.copy(day = day, month = month, year = year)
    }

    fun onDialogVisibilityChange() {
        _uiState.value = _uiState.value.copy(isDialogVisible = !isDialogVisible)
    }

    fun onConfirmClick() {
        try {
            val todayCalendar = Calendar.getInstance()
            val birthCalendar = Calendar.getInstance().apply {
                isLenient = false
                set(
                    _uiState.value.year.toInt(),
                    _uiState.value.month.toInt() - 1,
                    _uiState.value.day.toInt()
                )
            }
            if (birthCalendar.after(todayCalendar)) {
                viewModelScope.launch {
                    SnackbarManager.sendEvent(SnackbarEvent(message = "Invalid Date"))
                }
                return
            }
            var age = todayCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)
            if (todayCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--
            }
            _uiState.value = _uiState.value.copy(age = age)
            if (age >= 18) {
                onDialogVisibilityChange()
            } else {
                viewModelScope.launch {
                    SnackbarManager.sendEvent(SnackbarEvent(message = "Your age has to be greater than 18."))
                }
            }
        } catch (ex: IllegalArgumentException) {
            viewModelScope.launch {
                SnackbarManager.sendEvent(SnackbarEvent(message = "Invalid Date"))
            }
        }
    }
}

data class BasicInformationUiState(
    val firstName: String = "",
    val lastName: String = "",
    val day: String = "",
    val month: String = "",
    val year: String = "",
    val age: Int = 0,
    val isDialogVisible: Boolean = false
)