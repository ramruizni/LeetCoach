package com.puadevs.leetcoach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.puadevs.leetcoach.features.voicetext.view.VoiceTextScreen
import com.puadevs.leetcoach.ui.theme.LeetCoachTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeetCoachTheme {
                VoiceTextScreen()
            }
        }
    }
}