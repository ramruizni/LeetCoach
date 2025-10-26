package com.puadevs.leetcoach.chat.domain.usecases

import com.puadevs.leetcoach.chat.domain.ChatRepository

class GetProblemDescription(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(number: Int): String? {
        return chatRepository.getProblemDescription(number)
    }
}