package com.puadevs.leetcoach.features.voicetext.view

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.puadevs.leetcoach.features.voicetext.viewmodel.VoiceTextViewModel
import java.io.File

@Composable
fun VoiceTextScreen(
    viewModel: VoiceTextViewModel = viewModel()
) {
    val context = LocalContext.current

    val audioState by viewModel.audioState.collectAsStateWithLifecycle()

    val audioUri = remember {
        File(context.externalCacheDir, "recorded_audio.m4a").toString()
    }

    Scaffold { innerPadding ->
        val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            viewModel.setPermissionGranted(granted)
            if (!granted) {
                viewModel.setTranscription("Audio recording permission denied.")
            }
        }

        LaunchedEffect(Unit) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row() {
                Button(
                    onClick = {
                        if (!audioState.permissionGranted) {
                            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            return@Button
                        }
                        viewModel.setStartButtonEnabled(false)
                        viewModel.setStopButtonEnabled(true)
                        viewModel.start(audioUri = audioUri)
                    },
                    enabled = audioState.startButtonEnabled
                ) {
                    Text(
                        text = "Start"
                    )
                }
                Button(
                    onClick = {
                        viewModel.setStopButtonEnabled(false)
                        viewModel.setStartButtonEnabled(true)
                        viewModel.stop()
                    },
                    enabled = audioState.stopButtonEnabled
                ) {
                    Text(
                        text = "Stop"
                    )
                }
            }
            Text(
                text = audioState.transcription
            )
        }
    }
}