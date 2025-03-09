package com.example.bloom.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Chats(
    @DocumentId val id: String = "",
    val connectionId: String = "",
    val chats: List<Chat> = emptyList(),
)

data class Chat(
    val message: String = "",
    val isRead: Boolean = false,
    val receiverId: String = "",
    val timestamp: Timestamp = Timestamp.now()
)