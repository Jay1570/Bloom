package com.example.bloom.model

import kotlinx.serialization.Serializable

@Serializable
data class responsePrompt(
    val id:String,
    val userID: String,
    val promptID: String,
    val prompt: String,
    val answer: String
)
