package com.puadevs.leetcoach.voicetext.domain

interface VoiceTextRepository {

    fun startRecording(audioUri: String)

    fun stopRecording()

    suspend fun retrieveVoiceTextFrom(audioUri: String): String?
}