package com.example.bloom.screens.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.SnackbarEvent
import com.example.bloom.SnackbarManager
import com.example.bloom.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class LoginViewModel(val userPreference: UserPreference) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    fun onLoginClick(navigateTo: (String) -> Unit) {
        if (uiState.value.username.length == 10) { // Ensure 10-digit phone number
            userPreference.setUserId(uiState.value.username)
//            _uiState.update { it.copy(inProcess = true) }
//            sendRequest(
//                onSuccess = { verificationId ->
//
////                    _uiState.update { it.copy(verificationID = verificationId) }
//                    viewModelScope.launch(Dispatchers.Main) {
//                        navigateTo(verificationId)
//                        _uiState.update { it.copy(inProcess = false) }
//                    }
//                },
//                onFailure = { showSnackbar("Failed to get verification ID.") }
//            )
            navigateTo(uiState.value.username)
        } else {
            showSnackbar("Please enter a valid 10-digit phone number.")
        }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            SnackbarManager.sendEvent(SnackbarEvent(message))
        }
    }

    private fun sendRequest(onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {  // Use `viewModelScope` to manage lifecycle
            try {
                val client = OkHttpClient.Builder().build()
                val mediaType = "text/plain".toMediaType()
                val body = RequestBody.create(mediaType, "")
                val request = Request.Builder()
                    .url("https://cpaas.messagecentral.com/verification/v3/send?countryCode=91&customerId=C-794CDDF8402641E&flowType=SMS&mobileNumber=7201011509")
                    .post(body)
                    .addHeader("authToken", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDLTc5NENEREY4NDAyNjQxRSIsImlhdCI6MTc0MjkwMzEyMSwiZXhwIjoxOTAwNTgzMTIxfQ.brTUkUcAbqPqJ9v51rFoDMeOG3F9be-MxK8mvJX4jpOCJ5IBBzfqClMVrrH4OwsYr37Mgx9VwlXXXhef4OBJ5g")
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string() ?: ""

                if (response.isSuccessful) {
                    val jsonObject = JSONObject(responseBody)
                    val dataObject = jsonObject.getJSONObject("data")
                    val verificationID = dataObject.getString("verificationId")  // ✅ Correct JSON key
                    Log.d("json",verificationID.toString())

                    onSuccess(verificationID)  // ✅ Send back the verification ID
                } else {
                    onFailure("Failed to get verification ID")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure("Exception: ${e.localizedMessage}")
            }
        }
    }
}

data class LoginUiState(
    val code: String = "",
    val username: String = "",
    val verificationID: String = "",  // ✅ Added verificationID to UI state
    val inProcess: Boolean = false
)
