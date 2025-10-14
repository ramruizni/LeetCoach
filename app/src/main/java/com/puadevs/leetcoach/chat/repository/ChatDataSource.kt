package com.puadevs.leetcoach.chat.repository

interface ChatDataSource {
    suspend fun sendMessage(userMessage: String): String?
}