package com.puadevs.leetcoach.voicetext.repository

import com.puadevs.leetcoach.voicetext.domain.VoiceTextRepository

class VoiceTextRepositoryImpl(
    private val voiceTextDataSource: VoiceTextDataSource
): VoiceTextRepository {
    override fun startRecording(audioUri: String) {
        voiceTextDataSource.startRecording(audioUri)
    }

    override fun stopRecording() {
        voiceTextDataSource.stopRecording()
    }

    override suspend fun retrieveVoiceTextFrom(audioUri: String): String? {
        return voiceTextDataSource.retrieveVoiceTextFrom(audioUri)
    }
}