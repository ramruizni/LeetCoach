package com.puadevs.leetcoach.features.photo.viewmodel

import androidx.lifecycle.ViewModel
import com.puadevs.leetcoach.photo.domain.usecases.RetrieveTextFrom
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
   private val retrieveTextFrom: RetrieveTextFrom,
    // TODO: Inject observe all messages use case
): ViewModel() {
}