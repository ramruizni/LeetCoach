package com.puadevs.leetcoach.chat.datasource

import android.util.Log
import com.puadevs.leetcoach.chat.Constants.AI_MISTRAL_MODEL
import com.puadevs.leetcoach.chat.datasource.remote.LLMApi
import com.puadevs.leetcoach.chat.domain.models.ChatRequest
import com.puadevs.leetcoach.chat.domain.models.Message
import com.puadevs.leetcoach.chat.repository.ChatDataSource

class ChatDataSourceImpl(
    private val llmApiKey: String,
    private val llmApi: LLMApi
) : ChatDataSource {

    override suspend fun sendMessage(userMessage: String): String? {
        return try {
            val request = ChatRequest(
                model = AI_MISTRAL_MODEL,
                messages = listOf(
                    Message("system", "Eres un asistente útil."),
                    Message("user", userMessage)
                )
            )
            val response = llmApi.chat("Bearer $llmApiKey", request)

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