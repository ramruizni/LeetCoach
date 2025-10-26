package com.puadevs.leetcoach.chat.datasource

import android.util.Log
import com.puadevs.leetcoach.chat.Constants.CHAT_LLM_MODEL
import com.puadevs.leetcoach.chat.datasource.remote.ChatApi
import com.puadevs.leetcoach.chat.domain.models.ChatRequest
import com.puadevs.leetcoach.chat.domain.models.Message
import com.puadevs.leetcoach.chat.repository.ChatDataSource

class ChatDataSourceImpl(
    private val api: ChatApi,
    private val apiKey: String
) : ChatDataSource {

    override suspend fun sendMessage(userMessage: String): String? {
        return try {
            val request = ChatRequest(
                model = CHAT_LLM_MODEL,
                messages = listOf(
                    Message("system", "Eres un asistente útil."),
                    Message("user", userMessage)
                )
            )
            val response = api.chat("Bearer $apiKey", request)

            response.choices.firstOrNull()?.message?.content.orEmpty()
                .ifBlank { "El modelo no devolvió respuesta" }
        } catch (e: Exception) {
            Log.e(TAG, "Chat response failed: ${e.message}")
            null
        }
    }

    companion object {
        const val TAG = "ChatDataSource"
    }
}