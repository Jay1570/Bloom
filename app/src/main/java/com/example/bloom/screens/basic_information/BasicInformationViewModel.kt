package com.example.bloom.screens.basic_information

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.SnackbarEvent
import com.example.bloom.SnackbarManager
import com.example.bloom.UserPreference
import com.example.bloom.model.insertinfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.util.Calendar

class BasicInformationViewModel(private val userPreference: UserPreference) : ViewModel() {

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

            2 -> {
                incrementCurrentTab()
            }
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
                userPreference.setUserAge(age)
                Log.d("age",age.toString())
                //Log.d("info", "${userPreference.user.value} ${_uiState.value.firstName} ${_uiState.value.age}")
                senddata()

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

    private fun senddata(){
        CoroutineScope(Dispatchers.IO).launch {
            val userData= insertinfo(userID = userPreference.user.value,
                firstname = _uiState.value.firstName,
                lastname = _uiState.value.lastName,
                age = _uiState.value.age.toString())
            val client = OkHttpClient()
            val jsonMediaType = "application/json; charset=utf-8".toMediaType()
            val jsonBody = Json.encodeToString(userData)

            val requestBody = jsonBody.toRequestBody(jsonMediaType)
            val request = Request.Builder()
                .url("http://192.168.0.131:8080/insert")
                .post(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("failure","Error: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d("success",response.body?.string() ?: "No response")
                }
            })
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