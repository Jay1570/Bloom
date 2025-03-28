package com.example.bloom.screens.auth

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.bloom.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONObject

class VerificationViewModel(val userPreference: UserPreference ,val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val verificationId=savedStateHandle.toRoute<EmailVerification>().verificationID
    private val _uiState = MutableStateFlow(VerificationUiState())
    val uiState = _uiState.asStateFlow()

    fun onCodeChange(newValue: String) {
        _uiState.update {
            it.copy(code = newValue)
        }
    }

    fun verifyEmail(navigateTo: (Routes) -> Unit, ) {
        if(_uiState.value.code.trim().isNotEmpty()){
            _uiState.value = _uiState.value.copy(inProcess = true)
            var userExists=false
            viewModelScope.launch {
                _uiState.update { it.copy(inProcess = true) }
                Log.d("verification",verificationId)
//            if(verifyOtp(_uiState.value.code,verificationId)){
//                _uiState.update { it.copy(inProcess = false) } }
//                Log.d("otp","success")
                userExists=verifyUser(userPreference.user.value)
            }.invokeOnCompletion {
                if (userExists) {
                    Log.d("User Verification", "User exists, navigating to Home")
                    navigateTo(Home)
                } else {
                    Log.d("User Verification", "User not found, navigating to BasicInformation")
                    navigateTo(Home) //TODO: Basic Information screen
                }

            }
        }
        else{
            showSnackbar("Please enter verification code")
        }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            SnackbarManager.sendEvent(SnackbarEvent(message))
        }
    }

    data class VerificationUiState(
        val code: String = "",
        val inProcess: Boolean = false
    )

    suspend fun verifyUser(userid: String): Boolean{
        return withContext (Dispatchers.IO){
            try {
                val client= okhttp3.OkHttpClient()
                val request=Request.Builder()
                    .url("http://192.168.0.131:8080/checkuser/$userid").get()
                    .build()
                val response = client.newCall(request).execute()

                val responseBody = response.body?.string() ?: ""
                if(response.isSuccessful){
                    val JSONObject= JSONObject(responseBody)
                    val data=JSONObject.getBoolean("success")
                    data
                }else{
                    false
                }
            }catch (e:Exception) {
                e.printStackTrace()
                false
            }
        }
    }

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

