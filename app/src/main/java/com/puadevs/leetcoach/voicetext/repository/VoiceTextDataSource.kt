package com.puadevs.leetcoach.voicetext.repository

interface VoiceTextDataSource {

    fun startRecording(audioUri: String)

    fun stopRecording()

    suspend fun retrieveVoiceTextFrom(audioUri: String): String?
}