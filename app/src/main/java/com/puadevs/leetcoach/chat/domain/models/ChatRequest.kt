package com.puadevs.leetcoach.chat.domain.models

data class ChatRequest(
    val model: String,
    val messages: List<Message>
)