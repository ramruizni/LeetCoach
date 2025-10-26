package com.puadevs.leetcoach.chat.domain

import com.puadevs.leetcoach.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun startNewChat(problemNumber: Int)

    fun observeMessages(): Flow<List<Message>>

    suspend fun sendMessage(text: String)
}