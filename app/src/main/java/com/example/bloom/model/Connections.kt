package com.example.bloom.model

import com.google.firebase.firestore.DocumentId

data class Connections(
    @DocumentId val id: String = "",
    val user1: FirebaseUser = FirebaseUser(),
    val user2: FirebaseUser = FirebaseUser(),
)

data class FirebaseUser(
    val userId: String = "",
    val fcmToken: String = "",
)