package com.puadevs.leetcoach.chat.domain.usecases

import com.puadevs.leetcoach.chat.domain.ChatRepository

class SendMessage(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(message: String): String {
        return chatRepository.sendMessage(message)
    }
}