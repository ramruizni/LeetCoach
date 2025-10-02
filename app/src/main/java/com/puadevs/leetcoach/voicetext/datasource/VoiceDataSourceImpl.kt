package com.puadevs.leetcoach.voicetext.datasource

import android.net.Uri
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.puadevs.leetcoach.voicetext.datasource.remote.WhisperApi
import com.puadevs.leetcoach.voicetext.repository.VoiceTextDataSource
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class VoiceDataSourceImpl(
    private val whisperApi: WhisperApi
): VoiceTextDataSource {

    override suspend fun retrieveTextFrom(audioUri: String): String {
        if (audioUri.isNotEmpty()) {
            val audioFile = audioUri.toUri().toFile()
            val requestFile =
                RequestBody.create("audio/m4a".toMediaType(), audioFile)
            val body = MultipartBody.Part.createFormData(
                "file",
                audioFile.name,
                requestFile
            )
            val result = whisperApi.transcribe(body)
            return result.text
        } else {
            return "No se pudo grabar el audio o el archivo está vacío"
        }
    }
}