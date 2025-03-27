package com.example.bloom.screens.auth

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.bloom.EmailVerification
import com.squareup.okhttp.OkHttpClient
import okhttp3.Request
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttp
import okhttp3.RequestBody
import okhttp3.Response

class VerificationViewModel(val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val verificationId=savedStateHandle.toRoute<EmailVerification>().verificationID
    private val _uiState = MutableStateFlow(VerificationUiState())
    val uiState = _uiState.asStateFlow()

    fun onCodeChange(newValue: String) {
        _uiState.update {
            it.copy(code = newValue)
        }
    }

    fun verifyEmail(navigateTo: () -> Unit, ) {
        _uiState.value = _uiState.value.copy(inProcess = true)
        viewModelScope.launch {
            _uiState.update { it.copy(inProcess = true) }
            Log.d("verification",verificationId)
//            if(verifyOtp(_uiState.value.code,verificationId)){
//                _uiState.update { it.copy(inProcess = false) } }
//                Log.d("otp","success")
            }.invokeOnCompletion {
            navigateTo()
        }
    }

    data class VerificationUiState(
        val code: String = "",
        val inProcess: Boolean = false
    )

    suspend fun verifyOtp(code: String, verificationId: String): Boolean {
        return withContext(Dispatchers.IO) { // Ensures it runs on background thread
            try {
                val client = okhttp3.OkHttpClient.Builder().build()
                val request = Request.Builder()
                    .url("https://cpaas.messagecentral.com/verification/v3/validateOtp?countryCode=91&mobileNumber=7201011509&verificationId=$verificationId&customerId=C-794CDDF8402641E&code=$code") // ✅ Fixed string interpolation
                    .get()
                    .addHeader(
                        "authToken",
                        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDLTc5NENEREY4NDAyNjQxRSIsImlhdCI6MTc0MjkwMzEyMSwiZXhwIjoxOTAwNTgzMTIxfQ.brTUkUcAbqPqJ9v51rFoDMeOG3F9be-MxK8mvJX4jpOCJ5IBBzfqClMVrrH4OwsYr37Mgx9VwlXXXhef4OBJ5g"
                    )
                    .build()

                val response = client.newCall(request).execute()
                response.isSuccessful // ✅ Return true if response is successful, otherwise false
            } catch (e: Exception) {
                e.printStackTrace()
                false // ✅ Return false in case of exception
            }
        }
    }
}

