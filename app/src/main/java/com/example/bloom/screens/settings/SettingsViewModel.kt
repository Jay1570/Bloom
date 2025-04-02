package com.example.bloom.screens.settings

import androidx.compose.runtime.mutableStateOf
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.BloomApplication
import com.example.bloom.PORT_8000
import com.example.bloom.PORT_8080
import com.example.bloom.PORT_8100
import com.example.bloom.PORT_8200
import com.example.bloom.Theme
import com.example.bloom.UserPreference
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class SettingsViewModel(val userPreference: UserPreference) : ViewModel() {

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

    fun onDeleteClick(){
        val userID= userPreference.user.value.toString()
        Log.d("setting viewmodel userID",userID)
        viewModelScope.launch {
            deleteUser_basic(userID)
            deleteUser_advance(userID)
            deleteUser_prompts(userID)
            deleteUser_photos(userID)
        }
        deleteConnections(userID)
    }

    fun onSignoutClick(){
        userPreference.setUserId("")
    }

}

fun deleteConnections(currentUserId: String) {
    val db = FirebaseFirestore.getInstance()

    db.collection("connections")
        .where(
            Filter.or(
                Filter.equalTo("user1Id", currentUserId),
                Filter.equalTo("user2Id", currentUserId)
            )
        )
        .get()
        .addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                db.collection("connections").document(document.id).delete()
                    .addOnSuccessListener { println("Deleted ${document.id}") }
                    .addOnFailureListener { e -> println("Error deleting ${document.id}: ${e.message}") }
            }
        }
        .addOnFailureListener { e -> println("Error fetching documents: ${e.message}")}
}

private suspend fun deleteUser_basic(userId: String): Boolean = withContext(Dispatchers.IO) {
    val client = OkHttpClient()
    val url = "http://${PORT_8080}/delete/$userId" // Adjust the endpoint as needed

    val request = Request.Builder()
        .url(url)
        .delete()
        .build()

    try {
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            Log.e("API_ERROR", "Failed to delete user. Response Code: ${response.code}")
            return@withContext false
        }

        Log.d("API_SUCCESS", "User deleted successfully")
        return@withContext true
    } catch (e: IOException) {
        Log.e("API_ERROR", "Exception: ${e.message}")
        return@withContext false
    }
}

private suspend fun deleteUser_advance(userId: String): Boolean = withContext(Dispatchers.IO) {
    val client = OkHttpClient()
    val url = "http://${PORT_8000}/delete/$userId" // Adjust the endpoint as needed

    val request = Request.Builder()
        .url(url)
        .delete()
        .build()

    try {
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            Log.e("API_ERROR", "Failed to delete user. Response Code: ${response.code}")
            return@withContext false
        }

        Log.d("API_SUCCESS", "User deleted successfully")
        return@withContext true
    } catch (e: IOException) {
        Log.e("API_ERROR", "Exception: ${e.message}")
        return@withContext false
    }
}

private suspend fun deleteUser_prompts(userId: String): Boolean = withContext(Dispatchers.IO) {
    val client = OkHttpClient()
    val url = "http://${PORT_8100}/delete/$userId" // Adjust the endpoint as needed

    val request = Request.Builder()
        .url(url)
        .delete()
        .build()

    try {
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            Log.e("API_ERROR", "Failed to delete user. Response Code: ${response.code}")
            return@withContext false
        }

        Log.d("API_SUCCESS", "User deleted successfully")
        return@withContext true
    } catch (e: IOException) {
        Log.e("API_ERROR", "Exception: ${e.message}")
        return@withContext false
    }
}

private suspend fun deleteUser_photos(userId: String): Boolean = withContext(Dispatchers.IO) {
    val client = OkHttpClient()
    val url = "http://${PORT_8200}/delete/$userId" // Adjust the endpoint as needed

    val request = Request.Builder()
        .url(url)
        .delete()
        .build()

    try {
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            Log.e("API_ERROR", "Failed to delete user. Response Code: ${response.code}")
            return@withContext false
        }

        Log.d("API_SUCCESS", "User deleted successfully")
        return@withContext true
    } catch (e: IOException) {
        Log.e("API_ERROR", "Exception: ${e.message}")
        return@withContext false
    }
}


data class SettingsUiState(
    val isThemeDialogVisible: Boolean = false,
    val currentTheme: Theme = Theme.SYSTEM,
)