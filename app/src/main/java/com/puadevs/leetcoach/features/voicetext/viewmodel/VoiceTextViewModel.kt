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

data class VoiceTextState(
    val permissionGranted: Boolean = false,
    val startButtonEnabled: Boolean = true,
    val stopButtonEnabled: Boolean = false,
    val error: String? = null,
    val isRecording: Boolean = false,
)

@HiltViewModel
class VoiceTextViewModel @Inject constructor(
    private val retrieveVoiceTextFrom: RetrieveVoiceTextFrom,
    private val startRecording: StartRecording,
    private val stopRecording: StopRecording,
) : ViewModel() {

    private val _audioState = MutableStateFlow(VoiceTextState())
    val audioState = _audioState.asStateFlow()

    fun setPermissionGranted(permissionGranted: Boolean) {
        _audioState.update { it.copy(permissionGranted = permissionGranted) }
        if (!permissionGranted) {
            _audioState.update { it.copy(error = "Permission not granted") }
        }
    }

    fun setIsRecording(isRecording: Boolean) {
        _audioState.update { it.copy(isRecording = isRecording) }
    }

    fun start(audioUri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            startRecording(audioUri = audioUri)
        }
    }

    fun stop(audioUri: String, onSuccess: (text: String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            stopRecording()
            val transcript = retrieveVoiceTextFrom(audioUri)
            if (transcript != null) {
                onSuccess(transcript)
            } else {
                _audioState.update { it.copy(error = "It was not possible to obtain the transcript\n") }
            }
        }
    }
}