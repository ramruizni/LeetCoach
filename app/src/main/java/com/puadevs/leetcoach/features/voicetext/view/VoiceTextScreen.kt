package com.puadevs.leetcoach.features.voicetext.view

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.puadevs.leetcoach.features.photo.viewmodel.PhotoViewModel
import com.puadevs.leetcoach.features.chat.viewmodel.ChatViewModel
import com.puadevs.leetcoach.features.voicetext.viewmodel.VoiceTextViewModel
import java.io.File

@Composable
fun VoiceTextScreen(
    viewModel: VoiceTextViewModel = viewModel(),
    chatViewModel: ChatViewModel = viewModel(),
    photoViewModel: PhotoViewModel = viewModel()
) {
    val context = LocalContext.current

    val audioState by viewModel.audioState.collectAsStateWithLifecycle()
    val chatState by chatViewModel.chatState.collectAsStateWithLifecycle()
    val photoState by photoViewModel.photoState.collectAsStateWithLifecycle()

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
            photoViewModel.setPhotoUri(photoFile.toURI().toString())
            photoViewModel.getRecognizedText(photoFile.toURI().toString())
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        viewModel.setPermissionGranted(granted)
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
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

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            photoState.photoUri?.toUri().let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Captured photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Row() {
                Button(
                    onClick = {
                        if (audioState.isRecording) {
                            viewModel.stop(audioUri = audioFile.toURI().toString()) { text ->
                                // TODO: Send message to ChatVM
                                //chatViewModel.sendMessage(text)
                            }
                            viewModel.setIsRecording(false)
                            photoViewModel.setButtonEnabled(true)
                        } else {
                            viewModel.start(audioUri = audioFile.toString())
                            viewModel.setIsRecording(true)
                            photoViewModel.setButtonEnabled(false)
                        }
                    }
                ) {
                    Text(
                        text = if (audioState.isRecording) "Stop" else "Start"
                    )
                }
                Button(
                    onClick = {
                        viewModel.setStopButtonEnabled(false)
                        viewModel.setStartButtonEnabled(true)
                        viewModel.stop(audioUri = audioFile.toURI().toString()) { text ->
                            chatViewModel.receiveVoiceMessage(text = text)
                        }
                    },
                    enabled = audioState.stopButtonEnabled
                ) {
                    Text(
                        text = "Stop"
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
            Text(
                text = "chatViewModel.transcription?.orEmpty()"
            )
            Spacer(modifier = Modifier.height(24.dp))
            photoState.recognizedText?.let {
                Text("Detected Text: $it")
            }
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(
                    count = chatState.size
                ) {
                    Text(
                        text = chatState[it]
                    )
                }
            }
        }
    }
}