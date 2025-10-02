package com.puadevs.leetcoach.voicetext.repository

interface VoiceTextDataSource {

    suspend fun retrieveTextFrom(audioUri: String): String
    // TODO: implement suspend fun sendTextToLLM
}