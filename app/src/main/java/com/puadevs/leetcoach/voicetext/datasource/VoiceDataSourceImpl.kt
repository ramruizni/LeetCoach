package com.puadevs.leetcoach.voicetext.datasource

import android.util.Log
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

    override suspend fun retrieveVoiceTextFrom(audioUri: String): String? {
        return try {
            val audioFile = audioUri.toUri().toFile()
            val requestFile =
                RequestBody.create("audio/m4a".toMediaType(), audioFile)
            val body = MultipartBody.Part.createFormData(
                "file",
                audioFile.name,
                requestFile
            )
            whisperApi.transcribe(body).text
        } catch (e: Exception) {
            Log.e(TAG, "Text recognition failed: ${e.message}")
            null
        }
    }
    companion object {
        const val TAG = "VoiceDataSource"
    }
}