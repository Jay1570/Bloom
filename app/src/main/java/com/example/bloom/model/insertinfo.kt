package com.example.bloom.model

import kotlinx.serialization.Serializable


@Serializable
data class insertinfo(
    val userID:String,
    val firstname: String,
    val lastname: String,
    val age : String
)
