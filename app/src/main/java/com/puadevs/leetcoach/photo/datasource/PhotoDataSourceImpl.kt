package com.puadevs.leetcoach.photo.datasource

import android.content.Context
import android.util.Log
import androidx.core.net.toUri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.puadevs.leetcoach.photo.repository.PhotoDataSource
import kotlinx.coroutines.tasks.await

class PhotoDataSourceImpl(
    private val context: Context
) : PhotoDataSource {

    override suspend fun retrieveTextFrom(imageUri: String): String? {
        val image = InputImage.fromFilePath(context, imageUri.toUri())
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        return try {
            recognizer
                .process(image)
                .await().text.ifEmpty { "No se encontró texto en la imagen." }
        } catch (e: Exception) {
            Log.e(TAG, "Text recognition failed: ${e.message}")
            null
        }
    }

    companion object {
        const val TAG = "PhotoDataSource"
    }
}