package com.puadevs.leetcoach.chat.domain.models

enum class MessageRole {
    USER,
    SYSTEM
}

data class Message(
    val role: MessageRole,
    val content: String
)

