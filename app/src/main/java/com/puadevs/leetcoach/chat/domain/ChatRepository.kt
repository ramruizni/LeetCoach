package com.puadevs.leetcoach.chat.domain

interface ChatRepository {
    suspend fun sendMessage(userMessage: String): String
}