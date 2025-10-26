package com.puadevs.leetcoach.chat.datasource

import android.content.Context
import android.util.Log
import com.puadevs.leetcoach.chat.Constants.CHAT_LLM_MODEL
import com.puadevs.leetcoach.chat.datasource.remote.ChatApi
import com.puadevs.leetcoach.chat.datasource.dtos.ChatRequestDto
import com.puadevs.leetcoach.chat.datasource.dtos.MessageDto
import com.puadevs.leetcoach.chat.domain.models.Message
import com.puadevs.leetcoach.chat.repository.ChatDataSource

class ChatDataSourceImpl(
    private val context: Context,
    private val api: ChatApi,
    private val apiKey: String
) : ChatDataSource {

    private val systemPrompt: String by lazy {
        context.assets.open("prompts/leetcode_coach.md")
            .bufferedReader()
            .use { it.readText() }
    }

    override suspend fun sendMessage(userMessage: String): String? {
        return try {
            val request = ChatRequestDto(
                model = CHAT_LLM_MODEL,
                messages = listOf(
                    MessageDto("system", systemPrompt),
                    MessageDto("user", userMessage)
                )
            )
            val response = api.chat("Bearer $apiKey", request)

            response.choices.firstOrNull()?.message?.content.orEmpty()
                .ifBlank { "No answer received." }
        } catch (e: Exception) {
            Log.e(TAG, "Chat response failed: ${e.message}")
            null
        }
    }

    companion object {
        const val TAG = "ChatDataSource"
    }
}