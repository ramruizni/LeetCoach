package com.puadevs.leetcoach.voicetext.repository

interface VoiceTextDataSource {

    suspend fun startRecording(audioUri: String)

    suspend fun stopRecording()

    suspend fun retrieveVoiceTextFrom(audioUri: String): String?
}