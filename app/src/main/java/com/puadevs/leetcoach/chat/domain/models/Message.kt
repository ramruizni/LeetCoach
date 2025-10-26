package com.puadevs.leetcoach.chat.domain.models

enum class MessageRole {
    SYSTEM,
    USER,
    ASSISTANT
}

data class Message(
    val role: MessageRole,
    val content: String
)