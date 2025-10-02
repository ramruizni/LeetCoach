package com.puadevs.leetcoach.voicetext.datasource.remote

import com.puadevs.leetcoach.voicetext.datasource.remote.dtos.TranscriptionResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface WhisperApi {
    @Multipart
    @POST("audio/transcriptions")
    suspend fun transcribe(
        @Part file: MultipartBody.Part,
        @Part("model") model: RequestBody =
            RequestBody.create("text/plain".toMediaType(), "whisper-1")
    ): TranscriptionResponse
}