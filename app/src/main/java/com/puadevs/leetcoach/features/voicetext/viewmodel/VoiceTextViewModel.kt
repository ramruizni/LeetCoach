package com.puadevs.leetcoach.features.voicetext.viewmodel

import androidx.lifecycle.ViewModel
import com.puadevs.leetcoach.voicetext.domain.usecases.RetrieveVoiceTextFrom
import com.puadevs.leetcoach.voicetext.domain.usecases.StartRecording
import com.puadevs.leetcoach.voicetext.domain.usecases.StopRecording
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VoiceTextViewModel @Inject constructor(
    private val retrieveVoiceTextFrom: RetrieveVoiceTextFrom,
    private val startRecording: StartRecording,
    private val stopRecording: StopRecording
) : ViewModel() {
}