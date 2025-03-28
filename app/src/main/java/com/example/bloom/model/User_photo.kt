package com.example.bloom.model

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class User_photo(
    val id:String= ObjectId().toHexString(),
    val userID: String,
    val photoID: String,
    val url : String
)
