package com.puadevs.leetcoach.chat.repository

import com.puadevs.leetcoach.chat.domain.ChatRepository

class ChatRepositoryImpl(
    private val chatDataSource: ChatDataSource
): ChatRepository {
    override suspend fun sendMessage(userMessage: String): String {
        return chatDataSource.sendMessage(userMessage)
    }
}