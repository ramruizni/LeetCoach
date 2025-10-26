package com.puadevs.leetcoach.chat.datasource.dtos

data class ChatRequestDto(
    val model: String,
    val messages: List<MessageDto>
)