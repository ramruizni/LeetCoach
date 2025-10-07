package com.puadevs.leetcoach.voicetext.datasource

import android.media.MediaRecorder
import android.util.Log
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.puadevs.leetcoach.voicetext.datasource.remote.WhisperApi
import com.puadevs.leetcoach.voicetext.repository.VoiceTextDataSource
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class VoiceDataSourceImpl(
    private val whisperApi: WhisperApi,
): VoiceTextDataSource {

    private var mediaRecorder: MediaRecorder? = null

    override suspend fun startRecording(audioUri: String) {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(audioUri)
            prepare()
            start()
        }
    }

    override suspend fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }


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