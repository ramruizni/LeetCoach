package com.puadevs.leetcoach.photo.domain.usecases

import com.puadevs.leetcoach.photo.domain.PhotoRepository

class RetrieveTextFrom(
    private val photoRepository: PhotoRepository
) {
    suspend operator fun invoke(imageUri: String): String? {
        return photoRepository.retrieveTextFrom(imageUri)
    }
}