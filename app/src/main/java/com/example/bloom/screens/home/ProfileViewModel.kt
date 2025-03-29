package com.example.bloom.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.PORT_8000
import com.example.bloom.PORT_8080
import com.example.bloom.PORT_8100
import com.example.bloom.PORT_8200
import com.example.bloom.SnackbarEvent
import com.example.bloom.SnackbarManager
import com.example.bloom.UserPreference
import com.example.bloom.model.insertinfo
import com.example.bloom.model.insertinformation
import com.example.bloom.model.responsePhoto
import com.example.bloom.model.responsePrompt
import com.example.bloom.screens.information.InformationUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ProfileViewModel(val userPreference: UserPreference) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _visibilityState = MutableStateFlow(VisibilityState())
    val visibilityState get() = _visibilityState.asStateFlow()

    var user_baisc: insertinfo? = null
    var info: insertinformation? = null
    var prompt: List<responsePrompt>? = null
    var photo: List<responsePhoto>? = null

    init {
        viewModelScope.launch {
            user_baisc = fetchUsers(userPreference.user.value)
            info = fetchUsersInfo(userPreference.user.value)
            prompt = fetchPrompt(userPreference.user.value)
            photo = fetchUrl(userPreference.user.value)

            Log.d("url", photo.toString())

            if (user_baisc != null && info != null && prompt != null && photo != null) {
                _uiState.update {
                    it.copy(
                        informationUiState = InformationUiState(
                            locality = info!!.locality,
                            selectedPronouns = info!!.pronouns.map { it.toString() },
                            selectedGender = info!!.gender,
                            selectedSexuality = info!!.sexuality,
                            selectedDatingPreferences = info!!.datePrefrence.map { it.toString() },
                            selectedDatingIntention = info!!.datingintentions,
                            selectedRelationshipType = info!!.relationshiptype.map { it.toString() },
                            selectedHeightInCm = info!!.height.toInt(),
                            selectedEthnicity = info!!.ethnicity.map { it.toString() },
                            doYouHaveChildren = info!!.familyplan,
                            selectedFamilyPlan = info!!.haveChildren,
                            homeTown = info!!.hometown,
                            schoolOrCollege = info!!.school,
                            workPlace = info!!.work,
                            selectedEducation = info!!.educationlevel,
                            selectedReligiousBelief = info!!.religiousbelief,
                            selectedPoliticalBelief = info!!.politicalbelief,
                            selectedDrinkOption = info!!.drink,
                            selectedTobaccoOption = info!!.smoke,
                            selectedWeedOption = info!!.weed,
                            selectedDrugOption = info!!.drugs,
                        ),
                        name = user_baisc!!.firstname,
                        images = photo!!.map { it.url },
                        selectedTextPrompts = prompt!!.map { it.prompt to it.answer },
                        age = user_baisc!!.age.toInt()
                    )
                }
            }
        }
    }

    private suspend fun fetchPrompt(userID: String): List<responsePrompt>? =
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "http://${PORT_8100}/getinfo/$userID"
            val request = Request.Builder().url(url).get().build()

            try {
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) {
                    Log.e("API_ERROR_prompt", "Response Code: ${response.code}")
                    return@withContext null
                }
                response.body?.string()?.let {
                    Json.decodeFromString<List<responsePrompt>>(it)
                }
            } catch (e: IOException) {
                Log.e("API_ERROR", "Exception: ${e.message}")
                null
            }
        }

    private suspend fun fetchUrl(userID: String): List<responsePhoto>? =
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "http://${PORT_8200}/getinfo/$userID"
            val request = Request.Builder().url(url).get().build()

            try {
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) {
                    Log.e("API_ERROR_url", "Response Code: ${response.code}")
                    return@withContext null
                }
                response.body?.string()?.let {
                    Json.decodeFromString<List<responsePhoto>>(it)
                }
            } catch (e: IOException) {
                Log.e("API_ERROR", "Exception: ${e.message}")
                null
            }
        }

    private suspend fun fetchUsers(userID: String): insertinfo? = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://${PORT_8080}/getuser/$userID"
        val request = Request.Builder().url(url).get().build()

        try {
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.e("API_ERROR_info", "Response Code: ${response.code}")
                return@withContext null
            }
            response.body?.string()?.let {
                Json.decodeFromString<insertinfo>(it)
            }
        } catch (e: IOException) {
            Log.e("API_ERROR", "Exception: ${e.message}")
            null
        }
    }

    private suspend fun fetchUsersInfo(userID: String): insertinformation? =
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "http://${PORT_8000}/getinfo/$userID"
            val request = Request.Builder().url(url).get().build()

            try {
                val response = client.newCall(request).execute()
                if (!response.isSuccessful) {
                    Log.e("API_ERROR_info", "Response Code: ${response.code}")
                    return@withContext null
                }
                response.body?.string()?.let {
                    Json.decodeFromString<insertinformation>(it)
                }
            } catch (e: IOException) {
                Log.e("API_ERROR", "Exception: ${e.message}")
                null
            }
        }

    fun onEditClick() {
        viewModelScope.launch {
            val userData = insertinformation(
                userID = userPreference.user.value,
                locality = _uiState.value.informationUiState.locality ?: info!!.locality,
                pronouns = _uiState.value.informationUiState.selectedPronouns.firstOrNull()
                    ?: info!!.pronouns.toString(),
                gender = _uiState.value.informationUiState.selectedGender ?: info!!.gender,
                sexuality = _uiState.value.informationUiState.selectedSexuality ?: info!!.sexuality,
                datePrefrence = _uiState.value.informationUiState.selectedDatingPreferences.firstOrNull()
                    ?: info!!.datePrefrence.toString(),
                datingintentions = _uiState.value.informationUiState.selectedDatingIntention
                    ?: info!!.datingintentions,
                relationshiptype = _uiState.value.informationUiState.selectedRelationshipType.firstOrNull()
                    ?: info!!.relationshiptype.toString(),
                height = _uiState.value.informationUiState.selectedHeightInCm.toString(),
                ethnicity = _uiState.value.informationUiState.selectedEthnicity.firstOrNull()
                    ?: info!!.ethnicity.toString(),
                haveChildren = _uiState.value.informationUiState.doYouHaveChildren
                    ?: info!!.haveChildren,
                familyplan = _uiState.value.informationUiState.selectedFamilyPlan
                    ?: info!!.familyplan,
                hometown = _uiState.value.informationUiState.homeTown ?: info!!.hometown,
                school = _uiState.value.informationUiState.schoolOrCollege ?: info!!.school,
                work = _uiState.value.informationUiState.workPlace ?: info!!.work,
                educationlevel = _uiState.value.informationUiState.selectedEducation
                    ?: info!!.educationlevel,
                religiousbelief = _uiState.value.informationUiState.selectedReligiousBelief
                    ?: info!!.religiousbelief,
                politicalbelief = _uiState.value.informationUiState.selectedPoliticalBelief
                    ?: info!!.politicalbelief,
                drink = _uiState.value.informationUiState.selectedDrinkOption ?: info!!.drink,
                smoke = _uiState.value.informationUiState.selectedTobaccoOption ?: info!!.smoke,
                weed = _uiState.value.informationUiState.selectedWeedOption ?: info!!.weed,
                drugs = _uiState.value.informationUiState.selectedDrugOption ?: info!!.drugs
            )
            val client = OkHttpClient()
            val jsonMediaType = "application/json; charset=utf-8".toMediaType()
            val jsonBody = Json.encodeToString(userData)
            val userid = userPreference.user.value
            val requestBody = jsonBody.toRequestBody(jsonMediaType)
            val request = Request.Builder()
                .url("http://${PORT_8000}/updateinfo/$userid")
                .put(requestBody)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("failure", "Error: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.d("success", response.body?.string() ?: "No response")
                }
            })
        }
    }

    fun addOrRemovePronoun(pronoun: String) {
        _uiState.update {
            val selectedPronouns = it.informationUiState.selectedPronouns.toMutableList()
            if (selectedPronouns.contains(pronoun)) {
                selectedPronouns.remove(pronoun)
            } else if (selectedPronouns.size < 4) {
                selectedPronouns.add(pronoun)
            }
            it.copy(informationUiState = it.informationUiState.copy(selectedPronouns = selectedPronouns))
        }
    }

    fun changeSelectedGender(gender: String) {
        _uiState.update { it.copy(informationUiState = it.informationUiState.copy(selectedGender = gender)) }
    }

    fun changeSelectedSexuality(sexuality: String) {
        _uiState.update { it.copy(informationUiState = it.informationUiState.copy(selectedSexuality = sexuality)) }
    }

    fun addOrRemoveDatingPreference(preference: String) {
        _uiState.update {
            val selectedDatingPreferences =
                it.informationUiState.selectedDatingPreferences.toMutableList()
            if (selectedDatingPreferences.contains(preference)) {
                selectedDatingPreferences.remove(preference)
            } else {
                selectedDatingPreferences.add(preference)
            }
            it.copy(informationUiState = it.informationUiState.copy(selectedDatingPreferences = selectedDatingPreferences))
        }
    }

    fun changeDatingIntention(intention: String) {
        _uiState.update {
            it.copy(
                informationUiState = it.informationUiState.copy(
                    selectedDatingIntention = intention
                )
            )
        }
    }

    fun addOrRemoveRelationshipType(type: String) {
        _uiState.update {
            val selectedRelationshipType =
                it.informationUiState.selectedRelationshipType.toMutableList()
            if (selectedRelationshipType.contains(type)) {
                selectedRelationshipType.remove(type)
            } else {
                selectedRelationshipType.add(type)
            }
            it.copy(informationUiState = it.informationUiState.copy(selectedRelationshipType = selectedRelationshipType))
        }
    }

    fun changeSelectedHeightInCm(heightInCm: Int) {
        _uiState.update { it.copy(informationUiState = it.informationUiState.copy(selectedHeightInCm = heightInCm)) }
    }

    fun addOrRemoveEthnicity(ethnicity: String) {
        _uiState.update {
            val selectedEthnicity = it.informationUiState.selectedEthnicity.toMutableList()
            if (selectedEthnicity.contains(ethnicity)) {
                selectedEthnicity.remove(ethnicity)
            } else {
                selectedEthnicity.add(ethnicity)
            }
            it.copy(informationUiState = it.informationUiState.copy(selectedEthnicity = selectedEthnicity))
        }
    }

    fun changeDoYouHaveChildren(value: String) {
        _uiState.update { it.copy(informationUiState = it.informationUiState.copy(doYouHaveChildren = value)) }
    }

    fun changeFamilyPlan(plan: String) {
        _uiState.update { it.copy(informationUiState = it.informationUiState.copy(selectedFamilyPlan = plan)) }
    }

    fun onHomeTownChange(homeTown: String) {
        _uiState.update { it.copy(informationUiState = it.informationUiState.copy(homeTown = homeTown)) }
    }

    fun onSchoolOrCollegeChange(schoolOrCollege: String) {
        _uiState.update { it.copy(informationUiState = it.informationUiState.copy(schoolOrCollege = schoolOrCollege)) }
    }

    fun onWorkPlaceChange(workPlace: String) {
        _uiState.update { it.copy(informationUiState = it.informationUiState.copy(workPlace = workPlace)) }
    }

    fun changeSelectedEducation(education: String) {
        _uiState.update { it.copy(informationUiState = it.informationUiState.copy(selectedEducation = education)) }
    }

    fun changeSelectedReligiousBelief(religiousBelief: String) {
        _uiState.update {
            it.copy(
                informationUiState = it.informationUiState.copy(
                    selectedReligiousBelief = religiousBelief
                )
            )
        }
    }

    fun changeSelectedPoliticalBelief(politicalBelief: String) {
        _uiState.update {
            it.copy(
                informationUiState = it.informationUiState.copy(
                    selectedPoliticalBelief = politicalBelief
                )
            )
        }
    }

    fun changeSelectedDrinkOption(drinkOption: String) {
        _uiState.update {
            it.copy(
                informationUiState = it.informationUiState.copy(
                    selectedDrinkOption = drinkOption
                )
            )
        }
    }

    fun changeSelectedTobaccoOption(tobaccoOption: String) {
        _uiState.update {
            it.copy(
                informationUiState = it.informationUiState.copy(
                    selectedTobaccoOption = tobaccoOption
                )
            )
        }
    }

    fun changeSelectedWeedOption(weedOption: String) {
        _uiState.update { it.copy(informationUiState = it.informationUiState.copy(selectedWeedOption = weedOption)) }
    }

    fun changeSelectedDrugOption(drugOption: String) {
        _uiState.update { it.copy(informationUiState = it.informationUiState.copy(selectedDrugOption = drugOption)) }
    }

    fun onLocationChnage(newValue: String) {
        _uiState.update { it.copy(informationUiState = it.informationUiState.copy(locality = newValue)) }
    }

    fun addTextPrompt(index: Int, prompt: String, answer: String) {
        val newList = _uiState.value.selectedTextPrompts.toMutableList().apply {
            this[index] = Pair(prompt, answer)
            Log.d("prompts", "$prompt | $answer")
        }
        _uiState.update { it.copy(selectedTextPrompts = newList) }
    }

    fun removeTextPrompt(index: Int) {
        val newList = _uiState.value.selectedTextPrompts.toMutableList().apply {
            this[index] = null
        }
        _uiState.update { it.copy(selectedTextPrompts = newList) }
    }

    fun toggleTextPromptList() {
        _visibilityState.update { it.copy(textPromptList = !it.textPromptList) }
    }

    fun toggleTextField() {
        _visibilityState.update { it.copy(textPromptTextField = !it.textPromptTextField) }
    }

    fun toggleLocality() {
        _visibilityState.update { it.copy(locality = !it.locality) }
    }

    fun toggleSelectedPronouns() {
        _visibilityState.update { it.copy(selectedPronouns = !it.selectedPronouns) }
    }

    fun toggleSelectedGender() {
        _visibilityState.update { it.copy(selectedGender = !it.selectedGender) }
    }

    fun toggleSelectedSexuality() {
        _visibilityState.update { it.copy(selectedSexuality = !it.selectedSexuality) }
    }

    fun toggleSelectedDatingPreferences() {
        _visibilityState.update { it.copy(selectedDatingPreferences = !it.selectedDatingPreferences) }
    }

    fun toggleSelectedDatingIntention() {
        _visibilityState.update { it.copy(selectedDatingIntention = !it.selectedDatingIntention) }
    }

    fun toggleSelectedRelationshipType() {
        _visibilityState.update { it.copy(selectedRelationshipType = !it.selectedRelationshipType) }
    }

    fun toggleSelectedHeightInCm() {
        _visibilityState.update { it.copy(selectedHeightInCm = !it.selectedHeightInCm) }
    }

    fun toggleSelectedEthnicity() {
        _visibilityState.update { it.copy(selectedEthnicity = !it.selectedEthnicity) }
    }

    fun toggleDoYouHaveChildren() {
        _visibilityState.update { it.copy(doYouHaveChildren = !it.doYouHaveChildren) }
    }

    fun toggleSelectedFamilyPlan() {
        _visibilityState.update { it.copy(selectedFamilyPlan = !it.selectedFamilyPlan) }
    }

    fun toggleHomeTown() {
        _visibilityState.update { it.copy(homeTown = !it.homeTown) }
    }

    fun toggleSchoolOrCollege() {
        _visibilityState.update { it.copy(schoolOrCollege = !it.schoolOrCollege) }
    }

    fun toggleWorkPlace() {
        _visibilityState.update { it.copy(workPlace = !it.workPlace) }
    }

    fun toggleSelectedEducation() {
        _visibilityState.update { it.copy(selectedEducation = !it.selectedEducation) }
    }

    fun toggleSelectedReligiousBelief() {
        _visibilityState.update { it.copy(selectedReligiousBelief = !it.selectedReligiousBelief) }
    }

    fun toggleSelectedPoliticalBelief() {
        _visibilityState.update { it.copy(selectedPoliticalBelief = !it.selectedPoliticalBelief) }
    }

    fun toggleSelectedDrinkOption() {
        _visibilityState.update { it.copy(selectedDrinkOption = !it.selectedDrinkOption) }
    }

    fun toggleSelectedTobaccoOption() {
        _visibilityState.update { it.copy(selectedTobaccoOption = !it.selectedTobaccoOption) }
    }

    fun toggleSelectedWeedOption() {
        _visibilityState.update { it.copy(selectedWeedOption = !it.selectedWeedOption) }
    }

    fun toggleSelectedDrugOption() {
        _visibilityState.update { it.copy(selectedDrugOption = !it.selectedDrugOption) }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            SnackbarManager.sendEvent(SnackbarEvent(message))
        }
    }
}

data class ProfileUiState(
    val name: String = "",
    val age: Int = 0,
    val images: List<String> = List(6) { "" },
    val selectedTextPrompts: List<Pair<String, String>?> = List(3) { null },
    val informationUiState: InformationUiState = InformationUiState(),
)

data class VisibilityState(
    val textPromptTextField: Boolean = false,
    val textPromptList: Boolean = false,
    val locality: Boolean = false,
    val selectedPronouns: Boolean = false,
    val selectedGender: Boolean = false,
    val selectedSexuality: Boolean = false,
    val selectedDatingPreferences: Boolean = false,
    val selectedDatingIntention: Boolean = false,
    val selectedRelationshipType: Boolean = false,
    val selectedHeightInCm: Boolean = false,
    val selectedEthnicity: Boolean = false,
    val doYouHaveChildren: Boolean = false,
    val selectedFamilyPlan: Boolean = false,
    val homeTown: Boolean = false,
    val schoolOrCollege: Boolean = false,
    val workPlace: Boolean = false,
    val selectedEducation: Boolean = false,
    val selectedReligiousBelief: Boolean = false,
    val selectedPoliticalBelief: Boolean = false,
    val selectedDrinkOption: Boolean = false,
    val selectedTobaccoOption: Boolean = false,
    val selectedWeedOption: Boolean = false,
    val selectedDrugOption: Boolean = false
)
