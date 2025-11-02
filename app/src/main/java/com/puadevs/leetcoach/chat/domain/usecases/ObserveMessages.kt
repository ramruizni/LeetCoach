package com.puadevs.leetcoach.chat.domain.usecases

import com.puadevs.leetcoach.chat.domain.ChatRepository
import com.puadevs.leetcoach.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow

class ObserveMessages(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(): Flow<List<Message>> = chatRepository.observeMessages()
}