package com.puadevs.leetcoach.photo.datasource

import android.content.Context
import androidx.core.net.toUri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.puadevs.leetcoach.photo.repository.PhotoDataSource
import kotlinx.coroutines.tasks.await

class PhotoDataSourceImpl(
    private val context: Context,
    private val imageUri: String
): PhotoDataSource {

    override suspend fun retrieveTextFrom(imageUri: String): String? {
        val image = InputImage.fromFilePath(context, imageUri.toUri())
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        try {
            val visionText = recognizer.process(image).await()
            return visionText.text.ifEmpty { "No se encontró texto en la imagen." }
        } catch (e: Exception) {
            throw Exception("Falló el reconocimiento de texto: ${e.localizedMessage}", e)
        }
    }
}