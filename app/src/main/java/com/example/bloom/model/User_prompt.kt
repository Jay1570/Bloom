package com.example.bloom.model

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class User_prompt(
    val id:String= ObjectId().toHexString(),
    val userID: String,
    val promptID: String,
    val prompt: String,
    val answer: String
)
