package com.example.bloom.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Chats(
    val id: String = "",
    val chats: List<Chat> = emptyList()
)

data class Chat(
    @DocumentId val id: String = "",
    val read: Boolean = false,
    val senderId: String = "",
    val message: String = "",
    val timestamp: Timestamp = Timestamp.now()
)