package com.example.bloom.screens.information

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InformationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InformationUiState())
    val uiState get() = _uiState.asStateFlow()

    private val currentTab get() = _uiState.value.currentTab

    fun goToNext() {
        incrementTab()
        /*        when (currentTab) {

                }*/
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
            it.copy(selectedDatingPreferences = selectedRelationshipType)
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
}

data class InformationUiState(
    val currentTab: Int = 0,
    val selectedPronouns: List<String> = emptyList(),
    val selectedGender: String = "",
    val selectedSexuality: String = "",
    val selectedDatingPreferences: List<String> = emptyList(),
    val selectedDatingIntention: String = "",
    val selectedRelationshipType: List<String> = emptyList(),
    val selectedHeightInCm: Int = 121,
    val selectedEthnicity: List<String> = emptyList(),
    val doYouHaveChildren: String = "",
    val selectedFamilyPlan: String = ""
)