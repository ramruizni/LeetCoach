package com.puadevs.leetcoach.voicetext.domain

interface VoiceTextRepository {

    suspend fun startRecording(audioUri: String)

    suspend fun stopRecording()

    suspend fun retrieveVoiceTextFrom(audioUri: String): String?
}