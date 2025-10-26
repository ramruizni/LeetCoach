package com.puadevs.leetcoach.features.photo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puadevs.leetcoach.photo.domain.usecases.RetrieveTextFrom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PhotoState(
    val isLoading: Boolean = false,
    val buttonEnabled: Boolean = true
)

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val retrieveTextFrom: RetrieveTextFrom
) : ViewModel() {

    private val _state = MutableStateFlow(PhotoState())
    val state = _state.asStateFlow()

    private val _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    fun setButtonEnabled(buttonEnabled: Boolean) {
        _state.update { it.copy(buttonEnabled = buttonEnabled) }
    }

    fun getRecognizedText(imageUri: String, onSuccess: (text: String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            val transcript = retrieveTextFrom(imageUri = imageUri)
            _state.update { it.copy(isLoading = false) }
            if (transcript != null) {
                onSuccess(transcript)
            } else {
                _events.send(Event.ShowMessage("It was not possible to read the image"))
            }
        }
    }

    sealed class Event {
        data class ShowMessage(val message: String) : Event()
    }
}