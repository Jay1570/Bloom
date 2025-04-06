package com.example.bloom

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserPreference(private val context: Context) {

    companion object {
        const val PREFS_NAME = "user_prefs"
        const val USER_KEY = "userId"
        const val AGE_KEY = "userAge"
        const val GEN_KEY="userGender"
    }

    private val sharedPreferences by lazy {
        context.applicationContext.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }

    private val _userFlow by lazy { MutableStateFlow(getSavedUser()) }
    val user: StateFlow<String> by lazy { _userFlow }

    private val _genderFlow by lazy { MutableStateFlow(getSavedGender()) }
    val gender: StateFlow<String> by lazy { _genderFlow }

    private val _ageFlow by lazy { MutableStateFlow(getSavedAge()) }
    val age: StateFlow<Int> by lazy { _ageFlow }

    private fun getSavedAge(): Int {
        return sharedPreferences.getInt(AGE_KEY, 0) // Default age: 0
    }

    private fun getSavedUser(): String {
        return sharedPreferences.getString(USER_KEY, "") ?: ""
    }

    private fun getSavedGender(): String {
        return sharedPreferences.getString(GEN_KEY, "") ?: ""
    }

    fun setUserAge(userAge: Int) {
        sharedPreferences.edit { putInt(AGE_KEY, userAge) }
        _ageFlow.value = userAge
    }

    fun setUserGen(userGender: String) {
        sharedPreferences.edit { putString(GEN_KEY, userGender) }
        _genderFlow.value = userGender
    }

    fun setUserId(userId: String) {
        sharedPreferences.edit { putString(USER_KEY, userId) }
        _userFlow.value = userId
    }
}