package com.puadevs.leetcoach.voicetext.repository

import com.puadevs.leetcoach.voicetext.domain.VoiceTextRepository

class VoiceTextRepositoryImpl(
    private val voiceTextDataSource: VoiceTextDataSource
): VoiceTextRepository {
    override fun startRecording(audioUri: String) {
        TODO("Not yet implemented")
    }

    override fun stopRecording(audioUri: String) {
        TODO("Not yet implemented")
    }

    override suspend fun retrieveVoiceTextFrom(audioUri: String): String? {
        return voiceTextDataSource.retrieveVoiceTextFrom(audioUri)
    }
}