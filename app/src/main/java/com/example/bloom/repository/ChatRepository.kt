package com.example.bloom.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class ChatRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun generateToken() {
        try {
            val token = FirebaseMessaging.getInstance().token.await()
            Log.d("TAG", "token: $token")
        } catch (e: Exception) {
            throw e
        }
    }

    companion object {
        const val CONNECTIONS = "connections"
        const val CHATS = "chats"
    }
}