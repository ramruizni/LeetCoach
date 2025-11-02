package com.puadevs.leetcoach.features.start.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puadevs.leetcoach.chat.domain.usecases.StartNewChat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StartState(
    val isLoading: Boolean = false,
)

@HiltViewModel
class StartViewModel @Inject constructor(
    private val startNewChat: StartNewChat,
) : ViewModel() {

    private val _state = MutableStateFlow(StartState())
    val state = _state.asStateFlow()

    private val _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    fun start(problemNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            /*_state.update { it.copy(isLoading = true) }
            val description = startNewChat(problemNumber)
            _state.update { it.copy(isLoading = false) }
            if (description == null) {
                _events.send(Event.NavigateToChat)
            } else {
                _events.send(Event.ShowToast("Couldn't fetch problem"))
            }*/

            _events.send(Event.NavigateToChat)
        }
    }

    sealed class Event {
        data object NavigateToChat : Event()
        data class ShowToast(val message: String) : Event()
    }
}