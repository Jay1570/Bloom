package com.example.bloom.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Connections(
    @DocumentId val id: String = "",
    val user1Id: String = "",
    val user2Id: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val unreadCount: Int = 0,
    val lastSenderId: String = "",
    val lastMessage: String = "",
)