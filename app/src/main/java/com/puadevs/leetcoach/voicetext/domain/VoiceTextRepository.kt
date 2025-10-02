package com.puadevs.leetcoach.voicetext.domain

interface VoiceTextRepository {

    suspend fun retrieveTextFrom(audioUri: String): String
    // TODO: implement suspend fun sendTextToLLM
}