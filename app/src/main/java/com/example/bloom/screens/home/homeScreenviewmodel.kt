package com.example.bloom.screens.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.PORT_8000
import com.example.bloom.PORT_8080
import com.example.bloom.PORT_8100
import com.example.bloom.PORT_8200
import com.example.bloom.R
import com.example.bloom.SnackbarEvent
import com.example.bloom.SnackbarManager
import com.example.bloom.UserPreference
import com.example.bloom.model.Connections
import com.example.bloom.model.insertinfo
import com.example.bloom.model.insertinformation
import com.example.bloom.model.responsePhoto
import com.example.bloom.model.responsePrompt
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
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
import kotlin.collections.firstOrNull
import kotlin.collections.isNotEmpty

class HomeScreenViewModel(private val userPreference: UserPreference, private val context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    private val shownUserIds = mutableSetOf<String>()

    init {
        fetchUsersByAge()
    }

    fun fetchUsersByAge() {
        viewModelScope.launch {
            try {
                val age = userPreference.age.value.toString()
                val users = fetchUsersList(age)

                val filteredUsers = users.filter { it.userID != userPreference.user.value }

                _uiState.update { it.copy(
                    availableUsers = filteredUsers,
                    isLoading = false
                )}

                selectRandomUser()
            } catch (e: Exception) {
                showSnackbar("Failed to load users: ${e.message}")
                _uiState.update { it.copy(
                    isLoading = false
                )}
            }
        }
    }

    fun selectRandomUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val availableUsers = _uiState.value.availableUsers

            val unshownUsers = availableUsers.filter { it.userID !in shownUserIds }
            if (unshownUsers.isEmpty()) {
                shownUserIds.clear()
                _uiState.update { it.copy(
                    noMoreProfiles = true,
                    currentUserProfile = null,
                    isLoading = false,
                    )}
                return@launch
            }

            val randomUser = unshownUsers.random()
            shownUserIds.add(randomUser.userID)

            try {
                val userProfile = fetchCompleteUserProfile(randomUser)
                _uiState.update { it.copy(
                    currentUserProfile = userProfile,
                    isLoading = false,
                    noMoreProfiles = false
                )}
            } catch (e: Exception) {
                showSnackbar("Failed to load user profile: ${e.message}")
                _uiState.update { it.copy(
                    isLoading = false
                )}
                selectRandomUser()
            }
        }
    }

    fun onNextClicked() {
        selectRandomUser()
    }

    fun onlikeClicked(){
        _uiState.update { it.copy(availableUsers = _uiState.value.availableUsers.filter { it.userID !in shownUserIds }) }
    }

    fun onMatchConfirmed(matchedUserId: String) {
        viewModelScope.launch {
            try {
                createFirestoreConnection(userPreference.user.value, matchedUserId)
                selectRandomUser()
            } catch (e: Exception) {
                showSnackbar("Failed to create connection: ${e.message}")
            }
        }
    }

    private suspend fun fetchUsersList(age: String): List<insertinfo> = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://${PORT_8080}/getinfo"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.e("API_ERROR", "Response Code: ${response.code}")
                return@withContext emptyList()
            }

            val responseBody = response.body?.string()
            Log.d("API_RESPONSE", responseBody ?: "No response body")

            responseBody?.let {
                return@withContext Json.decodeFromString<List<insertinfo>>(it)
            } ?: emptyList()
        } catch (e: IOException) {
            Log.e("API_ERROR", "Exception: ${e.message}")
            return@withContext emptyList()
        }
    }

    private suspend fun fetchCompleteUserProfile(basicInfo: insertinfo): UserProfile = withContext(Dispatchers.IO) {
        val advancedInfo = fetchUserInfo(basicInfo.userID)
        val userPrompts = fetchUserPrompts(basicInfo.userID)
        val userPhotos = fetchUserPhotos(basicInfo.userID)

        return@withContext createUserProfile(basicInfo, advancedInfo, userPrompts, userPhotos)
    }

    private suspend fun fetchUserInfo(userId: String): insertinformation? = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://${PORT_8000}/getinfo/$userId"
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
                return@withContext Json.decodeFromString<insertinformation>(it)
            }
        } catch (e: IOException) {
            Log.e("API_ERROR", "Exception: ${e.message}")
        }
        null
    }

    private suspend fun fetchUserPrompts(userId: String): List<responsePrompt> = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://${PORT_8100}/getinfo/$userId"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.e("API_ERROR_prompt", "Response Code: ${response.code}")
                return@withContext emptyList()
            }

            val responseBody = response.body?.string()
            responseBody?.let {
                return@withContext Json.decodeFromString<List<responsePrompt>>(it)
            } ?: emptyList()
        } catch (e: IOException) {
            Log.e("API_ERROR", "Exception: ${e.message}")
            return@withContext emptyList()
        }
    }

    private suspend fun fetchUserPhotos(userId: String): List<responsePhoto> = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://${PORT_8200}/getinfo/$userId"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.e("API_ERROR_url", "Response Code: ${response.code}")
                return@withContext emptyList()
            }

            val responseBody = response.body?.string()
            responseBody?.let {
                return@withContext Json.decodeFromString<List<responsePhoto>>(it)
            } ?: emptyList()
        } catch (e: IOException) {
            Log.e("API_ERROR", "Exception: ${e.message}")
            return@withContext emptyList()
        }
    }

    private suspend fun createFirestoreConnection(currentUserId: String, matchedUserId: String) = withContext(Dispatchers.IO) {
        val firestore = FirebaseFirestore.getInstance()
        val connection = Connections(
            user1Id = currentUserId,
            user2Id = matchedUserId
        )

        try {
            Tasks.await(firestore.collection("connections").add(connection))
            Log.d("Firestore", "Connection created successfully")
        } catch (e: Exception) {
            Log.e("Firestore", "Error creating connection: ${e.message}")
            throw e
        }
    }

    private fun createUserProfile(
        basicInfo: insertinfo,
        advancedInfo: insertinformation?,
        prompts: List<responsePrompt>,
        photos: List<responsePhoto>
    ): UserProfile {
        val photoUrls = if (photos.isNotEmpty()) {
            photos.take(3).map { it.url }
        } else {
            emptyList()
        }

        val imageResources = if (photoUrls.isNotEmpty()) {
            photoUrls
        } else {
            listOf(
                R.drawable.horse_rider,
                R.drawable.horse_rider2,
                R.drawable.horse_rider3
            ).map { resId ->
                "android.resource://${context.packageName}/$resId"
            }
        }

        val name = "${basicInfo.firstname ?: "User"}, ${basicInfo.age}"

        val details = mutableListOf<Pair<Int, String>>()

        details.add(R.drawable.ac_1_date to basicInfo.age.toString())

        advancedInfo?.let { info ->
            info.height?.let { height ->
                details.add(R.drawable.ac_2_heightscale to "$height")
            }

            info.sexuality?.let { sexuality ->
                details.add(R.drawable.ac_2_sexuality to sexuality)
            }

            info.pronouns?.let { pronouns ->
                details.add(R.drawable.ac_2_pronoun_person to pronouns)
            }

            info.locality?.let { location ->
                details.add(R.drawable.ac_2_location to location)
            }

            info.work?.let { work ->
                details.add(R.drawable.ac_2_work to work)
            }

            info.educationlevel?.let { education ->
                details.add(R.drawable.ac_2_studylevel to education)
            }


        }

        val aboutMe = if (prompts.isNotEmpty()) {
            prompts.firstOrNull()?.answer ?: "I love exploring new places and meeting new people."
        } else {
            "I love exploring new places and meeting new people."
        }

        val activeStatus = "ACTIVE FROM 5PM TO 11PM EST"

        val location = advancedInfo?.locality?.uppercase() ?: "UNKNOWN LOCATION"

        return UserProfile(
            userID = basicInfo.userID,
            name = name,
            imageResId = imageResources,
            activeStatus = activeStatus,
            location = location,
            details = details,
            aboutMe = aboutMe
        )
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            SnackbarManager.sendEvent(SnackbarEvent(message))
        }
    }
}

data class HomeScreenState(
    val availableUsers: List<insertinfo> = emptyList(),
    val currentUserProfile: UserProfile? = null,
    val isLoading: Boolean = true,
    val noMoreProfiles: Boolean = false
)
