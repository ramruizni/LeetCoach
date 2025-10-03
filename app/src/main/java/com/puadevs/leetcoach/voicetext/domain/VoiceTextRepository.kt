package com.puadevs.leetcoach.voicetext.domain

interface VoiceTextRepository {

    suspend fun retrieveVoiceTextFrom(audioUri: String): String?
}