package com.puadevs.leetcoach.features.voicetext.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puadevs.leetcoach.voicetext.domain.usecases.RetrieveVoiceTextFrom
import com.puadevs.leetcoach.voicetext.domain.usecases.StartRecording
import com.puadevs.leetcoach.voicetext.domain.usecases.StopRecording
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class AudioState(
    val permissionGranted: Boolean = false,
    val transcription: String = "",
    val startButtonEnabled: Boolean = true,
    val stopButtonEnabled: Boolean = false
        )
@HiltViewModel
class VoiceTextViewModel @Inject constructor(
    private val retrieveVoiceTextFrom: RetrieveVoiceTextFrom,
    private val startRecording: StartRecording,
    private val stopRecording: StopRecording,
    application: Application
) : ViewModel() {

    private val audioFile: File = File(application.externalCacheDir, "recorded_audio.m4a")

    private val _audioState = MutableStateFlow(AudioState())
    val audioState = _audioState.asStateFlow()


    fun setPermissionGranted(permissionGranted: Boolean) {
        _audioState.update { it.copy(permissionGranted = permissionGranted) }
    }
    fun setTranscription(transcription: String) {
        _audioState.update { it.copy(transcription = transcription) }
    }

    fun setStartButtonEnabled(startButtonEnabled: Boolean) {
        _audioState.update { it.copy(startButtonEnabled = startButtonEnabled) }
    }

    fun setStopButtonEnabled(stopButtonEnabled: Boolean) {
        _audioState.update { it.copy(stopButtonEnabled = stopButtonEnabled) }
    }

    fun start() {
        viewModelScope.launch(Dispatchers.IO) {
            startRecording(audioUri = audioFile.toString())
        }
    }

    fun stop() {
        viewModelScope.launch(Dispatchers.IO) {
            _audioState.update { it.copy(transcription = "Transcribing") }
            stopRecording()
        }
    }

    fun transcriptAudio() {
        viewModelScope.launch(Dispatchers.IO) {
            val transcript = retrieveVoiceTextFrom(audioUri = audioFile.toURI().toString())
            if (transcript != null) {
                _audioState.update { it.copy(transcription = transcript) }
            } else {
                _audioState.update { it.copy(transcription = "It was not possible to obtain the transcript\n") }
            }
        }
    }
}