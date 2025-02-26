package com.example.bloom.screens.basic_information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.SnackbarEvent
import com.example.bloom.SnackbarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class BasicInformationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BasicInformationUiState())
    val uiState get() = _uiState.asStateFlow()

    private val isDialogVisible: Boolean get() = _uiState.value.isDialogVisible
    private val currentTab: Int get() = _uiState.value.currentTab
    private val firstName: String get() = _uiState.value.firstName
    private val lastName: String get() = _uiState.value.lastName
    private val day: String get() = _uiState.value.day
    private val month: String get() = _uiState.value.month
    private val year: String get() = _uiState.value.year
    private val age: Int get() = _uiState.value.age

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

    fun goToPrevious() {
        _uiState.update { it.copy(currentTab = currentTab - 1) }
    }

    fun goToNext() {
        when (currentTab) {
            0 -> if (firstName.isNotEmpty()) incrementCurrentTab() else showSnackbar("Every Field is mandatory")
            1 -> if (day.isNotEmpty() && year.isNotEmpty() && month.isNotEmpty()) onConfirmClick() else showSnackbar(
                "Every Field is mandatory"
            )

            2 -> incrementCurrentTab()
        }
    }

    private fun incrementCurrentTab() {
        _uiState.update { it.copy(currentTab = currentTab + 1) }
    }

    fun onDialogConfirmClick() {
        _uiState.update { it.copy(currentTab = currentTab + 1) }
    }

    fun onConfirmClick() {
        try {
            val todayCalendar = Calendar.getInstance()
            val birthCalendar = Calendar.getInstance().apply {
                isLenient = false
                set(
                    _uiState.value.year.trim().toInt(),
                    _uiState.value.month.trim().toInt() - 1,
                    _uiState.value.day.trim().toInt()
                )
            }
            if (birthCalendar.after(todayCalendar)) {
                showSnackbar("Invalid Date")
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
                showSnackbar("Your age has to be greater than 18")
            }
        } catch (ex: IllegalArgumentException) {
            showSnackbar("Invalid Date")
        }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            SnackbarManager.sendEvent(SnackbarEvent(message))
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
    val isDialogVisible: Boolean = false,
    val currentTab: Int = 0
)