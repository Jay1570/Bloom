package com.example.bloom

import kotlinx.serialization.Serializable

@Serializable
object Auth {
    @Serializable
    data object Intro

    @Serializable
    data object Login

    @Serializable
    data object Registration
}

@Serializable
object Home {
    @Serializable
    data object Explore

    @Serializable
    data object Profile

    @Serializable
    data object Connection

    @Serializable
    data object LikedYou
}