package com.puadevs.leetcoach.chat.repository

import com.puadevs.leetcoach.chat.domain.ChatRepository
import com.puadevs.leetcoach.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow

class ChatRepositoryImpl(
    private val chatDataSource: ChatDataSource
) : ChatRepository {

    override suspend fun startNewChat(problemNumber: Int): String? {
        return chatDataSource.startNewChat(problemNumber)
    }

    override fun observeMessages(): Flow<List<Message>> = chatDataSource.observeMessages()

    override suspend fun sendMessage(text: String) {
        return chatDataSource.sendMessage(text)
    }
}