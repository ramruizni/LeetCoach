package com.puadevs.leetcoach.voicetext.domain.models

import android.media.MediaRecorder
import java.io.File

class AudioRecorder(private val outputFile: File) {
    private var recorder: MediaRecorder? = null
    fun start() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile.absolutePath)
            prepare()
            start()
        }
    }

    fun stop() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }
}