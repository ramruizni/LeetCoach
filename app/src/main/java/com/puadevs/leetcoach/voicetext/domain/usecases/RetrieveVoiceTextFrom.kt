package com.puadevs.leetcoach.voicetext.domain.usecases

import com.puadevs.leetcoach.voicetext.domain.VoiceTextRepository

class RetrieveVoiceTextFrom(
    private val voiceTextRepository: VoiceTextRepository
) {
    suspend operator fun invoke(audioUri: String): String? {
        return voiceTextRepository.retrieveVoiceTextFrom(audioUri)
    }
}