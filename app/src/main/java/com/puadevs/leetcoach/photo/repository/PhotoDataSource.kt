package com.puadevs.leetcoach.photo.repository

interface PhotoDataSource {
    suspend fun retrieveTextFrom(imageUri: String): String?
}