package com.puadevs.leetcoach.chat.repository

import com.puadevs.leetcoach.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow

interface ChatDataSource {

    suspend fun startNewChat(problemNumber: Int)

    fun observeMessages(): Flow<List<Message>>

    suspend fun sendMessage(text: String)
}