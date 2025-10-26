package com.puadevs.leetcoach.chat.datasource.remote

import com.puadevs.leetcoach.chat.datasource.dtos.ChatRequestDto
import com.puadevs.leetcoach.chat.datasource.dtos.ChatResponseDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
interface ChatApi {
    @POST("v1/chat/completions")
    suspend fun chat(
        @Header("Authorization") auth: String,
        @Body request: ChatRequestDto
    ): ChatResponseDto
}
