package com.puadevs.leetcoach.voicetext.domain.usecases

import com.puadevs.leetcoach.voicetext.domain.VoiceTextRepository

class StopRecording(
    private val voiceTextRepository: VoiceTextRepository
) {
    suspend operator fun invoke (audioUri: String) {
        voiceTextRepository.stopRecording(audioUri)
    }
}