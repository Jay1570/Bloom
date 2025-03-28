package com.example.bloom.model

import kotlinx.serialization.Serializable

@Serializable
data class responsePhoto(
    val id : String,
    val userID:String,
    val photoID: String,
    val url : String
)
