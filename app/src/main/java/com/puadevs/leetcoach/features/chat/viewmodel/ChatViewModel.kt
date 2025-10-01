package com.puadevs.leetcoach.features.chat.viewmodel

import androidx.lifecycle.ViewModel
import com.puadevs.leetcoach.chat.domain.usecases.SendMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessage: SendMessage,
    // TODO: Inject observeMessages use case
) : ViewModel() {
}