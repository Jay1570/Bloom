package com.example.bloom.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.PORT_8000
import com.example.bloom.PORT_8080
import com.example.bloom.PORT_8100
import com.example.bloom.PORT_8200
import com.example.bloom.R
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

class HomeScreenViewModel(private val userPreference: UserPreference) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    // Keep track of users we've already shown to avoid repetition
    private val shownUserIds = mutableSetOf<String>()

    init {
        fetchUsersByAge()
    }

    // Step 1: Fetch users with similar age
    fun fetchUsersByAge() {
        viewModelScope.launch {
            try {
                val age = userPreference.age.value.toString()
                val users = fetchUsersList(age)

                // Filter out current user
                val filteredUsers = users.filter { it.userID != userPreference.user.value }

                _uiState.update { it.copy(
                    availableUsers = filteredUsers,
                    isLoading = false
                )}

                // Select initial user
                selectRandomUser()
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    error = "Failed to load users: ${e.message}",
                    isLoading = false
                )}
            }
        }
    }

    // Step 2: Select a random user and fetch their complete profile
    fun selectRandomUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val availableUsers = _uiState.value.availableUsers

            // Filter out users we've already shown
            val unshownUsers = availableUsers.filter { it.userID !in shownUserIds }

            if (unshownUsers.isEmpty()) {
                // If we've shown all users, reset and start over
                shownUserIds.clear()
                _uiState.update { it.copy(
                    currentUserProfile = null,
                    isLoading = false,
                    noMoreProfiles = true
                )}
                return@launch
            }

            // Select a random user we haven't shown yet
            val randomUser = unshownUsers.random()
            shownUserIds.add(randomUser.userID)

            // Fetch complete profile for the selected user
            try {
                val userProfile = fetchCompleteUserProfile(randomUser)
                _uiState.update { it.copy(
                    currentUserProfile = userProfile,
                    isLoading = false,
                    noMoreProfiles = false
                )}
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    error = "Failed to load user profile: ${e.message}",
                    isLoading = false
                )}
                // Try another user if this one fails
                selectRandomUser()
            }
        }
    }

    // Handle "Next" button click
    fun onNextClicked() {
        selectRandomUser()
    }

    // Handle after match screen is shown
    fun onMatchConfirmed(matchedUserId: String) {
        viewModelScope.launch {
            try {
                // Create connection in Firestore
                createFirestoreConnection(userPreference.user.value, matchedUserId)
                // Select next user
                selectRandomUser()
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    error = "Failed to create match: ${e.message}"
                )}
            }
        }
    }

    // Network functions
    private suspend fun fetchUsersList(age: String): List<insertinfo> = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://${PORT_8080}/getinfo/$age"
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
        // Get detailed user information
        val advancedInfo = fetchUserInfo(basicInfo.userID)
        val userPrompts = fetchUserPrompts(basicInfo.userID)
        val userPhotos = fetchUserPhotos(basicInfo.userID)

        // Map these to a UserProfile object
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

    // Helper function to create a UserProfile from API responses
    private fun createUserProfile(
        basicInfo: insertinfo,
        advancedInfo: insertinformation?,
        prompts: List<responsePrompt>,
        photos: List<responsePhoto>
    ): UserProfile {
        // Convert photo URLs to resource IDs (in a real app, you'd load these via Coil/Glide)
        // For now, we'll use placeholder images
        val imageResIds = if (photos.isNotEmpty()) {
            List(3) { R.drawable.horse_rider } // Placeholders - replace with actual image loading
        } else {
            listOf(R.drawable.horse_rider, R.drawable.horse_rider2, R.drawable.horse_rider3)
        }

        // Create a name string
        val name = "${basicInfo.firstname ?: "User"}, ${basicInfo.age}"

        // Create appropriate details from the advanced info
        val details = mutableListOf<Pair<Int, String>>()

        // Add age
        details.add(R.drawable.ac_1_date to basicInfo.age.toString())

        // Add additional details if advanced info is available
        advancedInfo?.let { info ->
            // Height
            info.height?.let { height ->
                details.add(R.drawable.ac_2_heightscale to "$height")
            }

            // Sexuality
            info.sexuality?.let { sexuality ->
                details.add(R.drawable.ac_2_sexuality to sexuality)
            }

            // Gender/Pronouns
            info.pronouns?.let { pronouns ->
                details.add(R.drawable.ac_2_pronoun_person to pronouns)
            }

            // Location
            info.locality?.let { location ->
                details.add(R.drawable.ac_2_location to location)
            }

            // Work
            info.work?.let { work ->
                details.add(R.drawable.ac_2_work to work)
            }

            // Education
            info.educationlevel?.let { education ->

                details.add(R.drawable.ac_2_studylevel to education)
            }
        }

        // Create about me text from prompts
        val aboutMe = if (prompts.isNotEmpty()) {
            prompts.firstOrNull()?.answer ?: "I love exploring new places and meeting new people."
        } else {
            "I love exploring new places and meeting new people."
        }

        // Active status format
        val activeStatus = "ACTIVE FROM 5PM TO 11PM EST"

        // Location
        val location = advancedInfo?.locality?.uppercase() ?: "UNKNOWN LOCATION"

        return UserProfile(
            userID = basicInfo.userID,
            name = name,
            imageResId = imageResIds,
            activeStatus = activeStatus,
            location = location,
            details = details,
            aboutMe = aboutMe
        )
    }
}

// State class to hold all UI state
data class HomeScreenState(
    val availableUsers: List<insertinfo> = emptyList(),
    val currentUserProfile: UserProfile? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val noMoreProfiles: Boolean = false
)
