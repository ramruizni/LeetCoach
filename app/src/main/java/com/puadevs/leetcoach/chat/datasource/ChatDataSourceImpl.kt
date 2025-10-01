package com.puadevs.leetcoach.chat.datasource

import com.puadevs.leetcoach.chat.Constants.AI_MISTRAL_MODEL
import com.puadevs.leetcoach.chat.datasource.remote.LLMApi
import com.puadevs.leetcoach.chat.domain.models.ChatRequest
import com.puadevs.leetcoach.chat.domain.models.Message
import com.puadevs.leetcoach.chat.repository.ChatDataSource

class ChatDataSourceImpl(
    private val llmApiKey: String,
    private val llmApi: LLMApi
) : ChatDataSource {
    override suspend fun sendMessage(userMessage: String): String {
        val request = ChatRequest(
            model = AI_MISTRAL_MODEL,
            messages = listOf(
                Message("system", "Eres un asistente útil."),
                Message("user", userMessage)
            )
        )
        val response = llmApi.chat("Bearer $llmApiKey", request)
        return response.choices.firstOrNull()?.let { choice ->
            when {
                !choice.message?.content.isNullOrBlank() -> choice.message!!.content
                !choice.text.isNullOrBlank() -> choice.text
                else -> "El modelo no devolvió respuesta"
            }
        } ?: "Sin respuesta"
    }
}