package com.example.bloom

import kotlinx.serialization.Serializable

sealed interface Routes

@Serializable
data object Auth : Routes

@Serializable
data object Intro : Routes

@Serializable
data object Login : Routes

@Serializable
data object Registration : Routes


@Serializable
data object Home : Routes

@Serializable
data object Explore : Routes

@Serializable
data object Profile : Routes

@Serializable
data object Connection : Routes

@Serializable
data class Chat(
    val connectionId: Int,
    val name: String
) : Routes

@Serializable
data object LikedYou : Routes

@Serializable
data object Settings : Routes