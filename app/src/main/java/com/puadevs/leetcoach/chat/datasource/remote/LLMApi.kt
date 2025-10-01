package com.puadevs.leetcoach.chat.datasource.remote

import com.puadevs.leetcoach.chat.domain.models.ChatRequest
import com.puadevs.leetcoach.chat.domain.models.ChatResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
interface LLMApi {
    @POST("v1/chat/completions")
    suspend fun chat(
        @Header("Authorization") auth: String,
        @Body request: ChatRequest
    ): ChatResponse
}
