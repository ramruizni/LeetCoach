package com.puadevs.leetcoach.features.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puadevs.leetcoach.chat.domain.usecases.SendMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessage: SendMessage,
    // TODO: Inject observeMessages use case
) : ViewModel() {

    private val _chatState = MutableStateFlow<List<String>>(emptyList())
    val chatState = _chatState.asStateFlow()

    fun receiveVoiceMessage(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _chatState.value = _chatState.value + sendMessage(text)
        }
    }
}