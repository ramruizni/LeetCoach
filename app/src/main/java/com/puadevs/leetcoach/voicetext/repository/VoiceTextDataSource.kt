package com.puadevs.leetcoach.voicetext.repository

interface VoiceTextDataSource {

    suspend fun retrieveVoiceTextFrom(audioUri: String): String?
}