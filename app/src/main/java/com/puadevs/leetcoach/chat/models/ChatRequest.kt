package com.puadevs.leetcoach.chat.models

data class ChatRequest(
    val model: String,
    val messages: List<Message>
)