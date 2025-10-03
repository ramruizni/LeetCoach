package com.puadevs.leetcoach.voicetext.repository

interface VoiceTextDataSource {

    fun startRecording(audioUri: String)

    fun stopRecording(audioUri: String)

    suspend fun retrieveVoiceTextFrom(audioUri: String): String?
}