package com.puadevs.leetcoach.chat.domain.usecases

import com.puadevs.leetcoach.chat.domain.ChatRepository

class StartNewChat(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(problemNumber: Int) {
        return chatRepository.startNewChat(problemNumber)
    }
}