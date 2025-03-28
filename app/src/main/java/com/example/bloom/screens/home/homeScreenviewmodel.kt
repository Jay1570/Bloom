package com.example.bloom.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.UserPreference
import com.example.bloom.model.insertinfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class homeScreenviewmodel(userPreference: UserPreference): ViewModel() {
    private val _uiState = MutableStateFlow(homeScreenContent())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(
            age = userPreference.age.value
        ) }
    }

    fun fetchuser(){
        viewModelScope.launch {
            val users = fetchUsersByAge(_uiState.value.age.toString()) ?: emptyList()
            _uiState.update { it.copy(user = users) }
            Log.d("userlist",users.toString())
        }
    }

}

suspend fun fetchUsersByAge(age: String): List<insertinfo>? {
    return withContext(Dispatchers.IO) {
        val client = OkHttpClient()

        val url = "http://192.168.0.131:8080/getinfo/$age" // Adjust the URL
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.e("API_ERROR", "Response Code: ${response.code}")
                return@withContext null
            }

            val responseBody = response.body?.string()
            Log.d("API_RESPONSE", responseBody ?: "No response body")

            responseBody?.let {
                return@withContext Json.decodeFromString<List<insertinfo>>(it)
            }
        } catch (e: IOException) {
            Log.e("API_ERROR", "Exception: ${e.message}")
            return@withContext null
        }
    }
}

data class homeScreenContent(
    val user: List<insertinfo> = emptyList(),
    val age: Int=0
)

