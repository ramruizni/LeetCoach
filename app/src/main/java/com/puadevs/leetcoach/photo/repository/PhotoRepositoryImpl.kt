package com.puadevs.leetcoach.photo.repository

import com.puadevs.leetcoach.photo.domain.PhotoRepository

class PhotoRepositoryImpl(
    private val photoDataSource: PhotoDataSource
): PhotoRepository {
    override suspend fun retrieveTextFrom(imageUri: String): String? {
        return photoDataSource.retrieveTextFrom(imageUri)
    }
}