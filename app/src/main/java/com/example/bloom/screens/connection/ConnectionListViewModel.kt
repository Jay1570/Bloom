package com.example.bloom.screens.connection

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.PORT_8080
import com.example.bloom.UserPreference
import com.example.bloom.model.Connections
import com.example.bloom.model.insertinfo
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class ConnectionListViewModel(private val userPreference: UserPreference) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private var listenerRegistration: ListenerRegistration? = null

    private val _connections = MutableStateFlow<List<Connections>>(emptyList())
    val connection = _connections.asStateFlow()

    val currentUser get() = userPreference.user

    init {
        getConnections()
    }

    fun getusername(userID: String, callback: (String?) -> Unit) {
        viewModelScope.launch {
            val userInfo = fetchUserInfo(userID)
            callback(userInfo?.firstname)
        }
    }

    private fun getConnections() {
        listenerRegistration?.remove()
        Log.d("HomeViewModel", currentUser.value)
        listenerRegistration = firestore.collection("connections").where(
            Filter.or(
                Filter.equalTo("user1Id", currentUser.value),
                Filter.equalTo("user2Id", currentUser.value)
            )
        ).orderBy("timestamp", Query.Direction.DESCENDING).addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("HomeViewModel", "Error fetching connections", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val connections =
                    snapshot.documents.mapNotNull { it.toObject(Connections::class.java) }
                _connections.value = connections
            }
        }
    }

    private suspend fun fetchUserInfo(userId: String): insertinfo? = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://${PORT_8080}/getuser/$userId"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.e("API_ERROR_info", "Response Code: ${response.code}")
                return@withContext null
            }

            val responseBody = response.body?.string()
            responseBody?.let {
                return@withContext Json.decodeFromString<insertinfo>(it)
            }
        } catch (e: IOException) {
            Log.e("API_ERROR", "Exception: ${e.message}")
        }
        null
    }

    override fun onCleared() {
        super.onCleared()
        listenerRegistration?.remove()
    }
}