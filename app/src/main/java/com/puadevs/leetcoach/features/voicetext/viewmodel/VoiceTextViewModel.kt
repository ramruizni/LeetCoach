package com.puadevs.leetcoach.features.voicetext.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puadevs.leetcoach.voicetext.domain.usecases.RetrieveVoiceTextFrom
import com.puadevs.leetcoach.voicetext.domain.usecases.StartRecording
import com.puadevs.leetcoach.voicetext.domain.usecases.StopRecording
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VoiceTextState(
    val isLoading: Boolean = false,
    val permissionGranted: Boolean = false,
    val startButtonEnabled: Boolean = true,
    val stopButtonEnabled: Boolean = false,
    val isRecording: Boolean = false,
)

@HiltViewModel
class VoiceTextViewModel @Inject constructor(
    private val retrieveVoiceTextFrom: RetrieveVoiceTextFrom,
    private val startRecording: StartRecording,
    private val stopRecording: StopRecording,
) : ViewModel() {

    private val _state = MutableStateFlow(VoiceTextState())
    val state = _state.asStateFlow()

    private val _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    fun setPermissionGranted(permissionGranted: Boolean) {
        _state.update { it.copy(permissionGranted = permissionGranted) }
        if (!permissionGranted) {
            viewModelScope.launch {
                _events.send(Event.ShowMessage("Permission not granted"))
            }
        }
    }

    fun setIsRecording(isRecording: Boolean) {
        _state.update { it.copy(isRecording = isRecording) }
    }

    fun start(audioUri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            startRecording(audioUri = audioUri)
        }
    }

    fun stop(audioUri: String, onSuccess: (text: String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            stopRecording()
            val transcript = retrieveVoiceTextFrom(audioUri)
            _state.update { it.copy(isLoading = false) }
            if (transcript != null) {
                onSuccess(transcript)
            } else {
                _events.send(Event.ShowMessage("It was not possible to obtain the transcript"))
            }
        }
    }

    sealed class Event {
        data class ShowMessage(val message: String) : Event()
    }
}