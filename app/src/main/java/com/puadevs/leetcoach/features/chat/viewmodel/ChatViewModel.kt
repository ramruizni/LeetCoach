package com.puadevs.leetcoach.features.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puadevs.leetcoach.chat.domain.usecases.ObserveMessages
import com.puadevs.leetcoach.chat.domain.usecases.SendMessage
import com.puadevs.leetcoach.chat.domain.usecases.StartNewChat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatState(
    val isLoading: Boolean = false,
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val startNewChat: StartNewChat,
    private val observeMessages: ObserveMessages,
    private val sendMessage: SendMessage,
) : ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    val messages = observeMessages()
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    fun receiveMessage(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            sendMessage(text)
            _state.update { it.copy(isLoading = false) }
        }
    }

    sealed class Event {
        data class ShowMessage(val message: String) : Event()
    }
}