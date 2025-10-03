package com.puadevs.leetcoach.photo.domain

interface PhotoRepository {

    suspend fun retrieveTextFrom(imageUri: String): String?
}