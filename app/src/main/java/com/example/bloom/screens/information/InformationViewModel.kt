package com.example.bloom.screens.information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom.SnackbarEvent
import com.example.bloom.SnackbarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InformationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InformationUiState())
    val uiState get() = _uiState.asStateFlow()

    private val currentTab get() = _uiState.value.currentTab

    fun goToNext(navigateToNext: () -> Unit) {
        when (currentTab) {
            0 -> if(_uiState.value.locality.isNotEmpty()) incrementTab() else showSnackbar(
                "Please enter you current locality"
            )
            1 -> if (_uiState.value.selectedPronouns.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select at least one pronoun"
            )

            2 -> if (_uiState.value.selectedGender.isNotEmpty()) incrementTab() else showSnackbar("Please select a gender")
            3 -> if (_uiState.value.selectedSexuality.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select a sexuality"
            )

            4 -> if (_uiState.value.selectedDatingPreferences.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select at least one dating preference"
            )

            5 -> if (_uiState.value.selectedDatingIntention.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select a dating intention"
            )

            6 -> if (_uiState.value.selectedRelationshipType.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select at least one relationship type"
            )

            7 -> if (_uiState.value.selectedHeightInCm > 0) incrementTab() else showSnackbar("Please select a height")
            8 -> if (_uiState.value.selectedEthnicity.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select at least one ethnicity"
            )

            9 -> if (_uiState.value.doYouHaveChildren.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select a value"
            )

            10 -> if (_uiState.value.selectedFamilyPlan.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select a family plan"
            )

            11 -> if (_uiState.value.homeTown.isNotEmpty()) incrementTab() else showSnackbar("Please select a home town")
            12 -> if (_uiState.value.schoolOrCollege.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select a school or college"
            )

            13 -> if (_uiState.value.workPlace.isNotEmpty()) incrementTab() else showSnackbar("Please select a work place")
            14 -> if (_uiState.value.selectedEducation.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select a education"
            )

            15 -> if (_uiState.value.selectedReligiousBelief.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select a religious belief"
            )

            16 -> if (_uiState.value.selectedPoliticalBelief.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select a political belief"
            )

            17 -> if (_uiState.value.selectedDrinkOption.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select a drink option"
            )

            18 -> if (_uiState.value.selectedTobaccoOption.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select a tobacco option"
            )

            19 -> if (_uiState.value.selectedWeedOption.isNotEmpty()) incrementTab() else showSnackbar(
                "Please select a weed option"
            )

            20 -> if (_uiState.value.selectedDrugOption.isNotEmpty()) navigateToNext() else showSnackbar(
                "Please select a drug option"
            )
        }
    }

    private fun incrementTab() {
        _uiState.update { it.copy(currentTab = currentTab + 1) }
    }

    fun goToPrevious() {
        _uiState.update { it.copy(currentTab = currentTab - 1) }
    }

    fun addOrRemovePronoun(pronoun: String) {
        _uiState.update {
            val selectedPronouns = it.selectedPronouns.toMutableList()
            if (selectedPronouns.contains(pronoun)) {
                selectedPronouns.remove(pronoun)
            } else if (selectedPronouns.size < 4) {
                selectedPronouns.add(pronoun)
            }
            it.copy(selectedPronouns = selectedPronouns)
        }
    }

    fun changeSelectedGender(gender: String) {
        _uiState.update { it.copy(selectedGender = gender) }
    }

    fun changeSelectedSexuality(sexuality: String) {
        _uiState.update { it.copy(selectedSexuality = sexuality) }
    }

    fun addOrRemoveDatingPreference(preference: String) {
        _uiState.update {
            val selectedDatingPreferences = it.selectedDatingPreferences.toMutableList()
            if (selectedDatingPreferences.contains(preference)) {
                selectedDatingPreferences.remove(preference)
            } else {
                selectedDatingPreferences.add(preference)
            }
            it.copy(selectedDatingPreferences = selectedDatingPreferences)
        }
    }

    fun changeDatingIntention(intention: String) {
        _uiState.update { it.copy(selectedDatingIntention = intention) }
    }

    fun addOrRemoveRelationshipType(type: String) {
        _uiState.update {
            val selectedRelationshipType = it.selectedRelationshipType.toMutableList()
            if (selectedRelationshipType.contains(type)) {
                selectedRelationshipType.remove(type)
            } else {
                selectedRelationshipType.add(type)
            }
            it.copy(selectedRelationshipType = selectedRelationshipType)
        }
    }

    fun changeSelectedHeightInCm(heightInCm: Int) {
        _uiState.update { it.copy(selectedHeightInCm = heightInCm) }
    }

    fun addOrRemoveEthnicity(ethnicity: String) {
        _uiState.update {
            val selectedEthnicity = it.selectedEthnicity.toMutableList()
            if (selectedEthnicity.contains(ethnicity)) {
                selectedEthnicity.remove(ethnicity)
            } else {
                selectedEthnicity.add(ethnicity)
            }
            it.copy(selectedEthnicity = selectedEthnicity)
        }
    }

    fun changeDoYouHaveChildren(value: String) {
        _uiState.update { it.copy(doYouHaveChildren = value) }
    }

    fun changeFamilyPlan(plan: String) {
        _uiState.update { it.copy(selectedFamilyPlan = plan) }
    }

    fun onHomeTownChange(homeTown: String) {
        _uiState.update { it.copy(homeTown = homeTown) }
    }

    fun onSchoolOrCollegeChange(schoolOrCollege: String) {
        _uiState.update { it.copy(schoolOrCollege = schoolOrCollege) }
    }

    fun onWorkPlaceChange(workPlace: String) {
        _uiState.update { it.copy(workPlace = workPlace) }
    }

    fun changeSelectedEducation(education: String) {
        _uiState.update { it.copy(selectedEducation = education) }
    }

    fun changeSelectedReligiousBelief(religiousBelief: String) {
        _uiState.update { it.copy(selectedReligiousBelief = religiousBelief) }
    }

    fun changeSelectedPoliticalBelief(politicalBelief: String) {
        _uiState.update { it.copy(selectedPoliticalBelief = politicalBelief) }
    }

    fun changeSelectedDrinkOption(drinkOption: String) {
        _uiState.update { it.copy(selectedDrinkOption = drinkOption) }
    }

    fun changeSelectedTobaccoOption(tobaccoOption: String) {
        _uiState.update { it.copy(selectedTobaccoOption = tobaccoOption) }
    }

    fun changeSelectedWeedOption(weedOption: String) {
        _uiState.update { it.copy(selectedWeedOption = weedOption) }
    }

    fun changeSelectedDrugOption(drugOption: String) {
        _uiState.update { it.copy(selectedDrugOption = drugOption) }
    }

    fun onLocationChnage(newValue: String){
        _uiState.update { it.copy(locality = newValue) }
    }

    private fun showSnackbar(message: String) {
        viewModelScope.launch {
            SnackbarManager.sendEvent(SnackbarEvent(message))
        }
    }
}

data class InformationUiState(
    val locality: String="",
    val currentTab: Int = 0,
    val selectedPronouns: List<String> = emptyList(),
    val selectedGender: String = "",
    val selectedSexuality: String = "",
    val selectedDatingPreferences: List<String> = emptyList(),
    val selectedDatingIntention: String = "",
    val selectedRelationshipType: List<String> = emptyList(),
    val selectedHeightInCm: Int = 0,
    val selectedEthnicity: List<String> = emptyList(),
    val doYouHaveChildren: String = "",
    val selectedFamilyPlan: String = "",
    val homeTown: String = "",
    val schoolOrCollege: String = "",
    val workPlace: String = "",
    val selectedEducation: String = "",
    val selectedReligiousBelief: String = "",
    val selectedPoliticalBelief: String = "",
    val selectedDrinkOption: String = "",
    val selectedTobaccoOption: String = "",
    val selectedWeedOption: String = "",
    val selectedDrugOption: String = ""
)