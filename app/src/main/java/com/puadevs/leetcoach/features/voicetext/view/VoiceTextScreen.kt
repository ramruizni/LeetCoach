package com.puadevs.leetcoach.features.voicetext.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.puadevs.leetcoach.features.voicetext.viewmodel.VoiceTextViewModel

@Composable
fun VoiceTextScreen(
    viewModel: VoiceTextViewModel = viewModel()
) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
            ) { }
    }
}