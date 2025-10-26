package com.puadevs.leetcoach.features.chat.view

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.puadevs.leetcoach.features.chat.viewmodel.ChatViewModel
import com.puadevs.leetcoach.features.photo.viewmodel.PhotoViewModel
import com.puadevs.leetcoach.features.voicetext.viewmodel.VoiceTextViewModel
import java.io.File

@Composable
fun ChatScreen(
    chatViewModel: ChatViewModel = viewModel(),
    photoViewModel: PhotoViewModel = viewModel(),
    voiceTextViewModel: VoiceTextViewModel = viewModel(),
) {
    val context = LocalContext.current

    val audioState by voiceTextViewModel.state.collectAsStateWithLifecycle()
    val chatState by chatViewModel.state.collectAsStateWithLifecycle()
    val photoState by photoViewModel.state.collectAsStateWithLifecycle()

    val audioFile = remember {
        File(context.externalCacheDir, "recorded_audio.m4a")
    }

    val photoFile = remember {
        File(context.externalCacheDir, "photo.jpg")
    }

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        photoFile
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            photoViewModel.getRecognizedText(imageUri = photoFile.toURI().toString()) { text ->
                chatViewModel.receiveMessage(text)
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        voiceTextViewModel.setPermissionGranted(granted)
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }

    LaunchedEffect(key1 = context) {
        chatViewModel.events.collect { event ->
            when (event) {
                is ChatViewModel.Event.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(key1 = context) {
        photoViewModel.events.collect { event ->
            when (event) {
                is PhotoViewModel.Event.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(key1 = context) {
        voiceTextViewModel.events.collect { event ->
            when (event) {
                is VoiceTextViewModel.Event.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row {
                Button(
                    onClick = {
                        if (audioState.isRecording) {
                            voiceTextViewModel.stop(
                                audioUri = audioFile.toURI().toString()
                            ) { text ->
                                chatViewModel.receiveMessage(text)
                            }
                            voiceTextViewModel.setIsRecording(false)
                            photoViewModel.setButtonEnabled(true)
                        } else {
                            voiceTextViewModel.start(audioUri = audioFile.toString())
                            voiceTextViewModel.setIsRecording(true)
                            photoViewModel.setButtonEnabled(false)
                        }
                    }
                ) {
                    Text(
                        text = if (audioState.isRecording) "Stop" else "Start"
                    )
                }
            }
            Button(
                onClick = {
                    cameraLauncher.launch(uri)
                },
                enabled = photoState.buttonEnabled
            ) {
                Text(
                    text = "Take photo"
                )
            }
        }
    }
}