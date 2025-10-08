package com.puadevs.leetcoach.features.voicetext.viewmodel

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
import javax.inject.Inject

data class AudioState(
    val permissionGranted: Boolean = false,
    val transcription: String = "",
    val isRecording: Boolean = false,
    val textButton: String = "Start",
)

@HiltViewModel
class VoiceTextViewModel @Inject constructor(
    private val retrieveVoiceTextFrom: RetrieveVoiceTextFrom,
    private val startRecording: StartRecording,
    private val stopRecording: StopRecording,
) : ViewModel() {

    private val _audioState = MutableStateFlow(AudioState())
    val audioState = _audioState.asStateFlow()

    fun setPermissionGranted(permissionGranted: Boolean) {
        _audioState.update { it.copy(permissionGranted = permissionGranted) }
    }

    fun setTranscription(transcription: String) {
        _audioState.update { it.copy(transcription = transcription) }
    }

    fun setIsRecording(isRecording: Boolean) {
        _audioState.update { it.copy(isRecording = isRecording) }
    }

    fun setTextButton(textButton: String) {
        _audioState.update { it.copy(textButton = textButton) }
    }

    fun start(audioUri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            startRecording(audioUri = audioUri)
        }
    }

    fun stop(audioUri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _audioState.update { it.copy(transcription = "Transcribing") }
            stopRecording()
            val transcript = retrieveVoiceTextFrom(audioUri)
            if (transcript != null) {
                _audioState.update { it.copy(transcription = transcript) }
            } else {
                _audioState.update { it.copy(transcription = "It was not possible to obtain the transcript\n") }
            }
        }
    }
}