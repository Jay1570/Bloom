package com.example.bloom.model

import kotlinx.serialization.Serializable

@Serializable
data class insertinformation(
    val userID:String,
    val locality:String,
    val pronouns: String,
    val gender: String,
    val sexuality: String,
    val datePrefrence: String,
    val datingintentions:String,
    val relationshiptype: String,
    val height: String,
    val ethnicity:String,
    val haveChildren:String,
    val familyplan: String,
    val hometown : String,
    val school:String,
    val work: String,
    val educationlevel: String,
    val religiousbelief : String,
    val politicalbelief:String,
    val drink: String,
    val smoke: String,
    val weed : String,
    val drugs : String
)
