package com.puadevs.leetcoach.features.photo.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puadevs.leetcoach.photo.domain.usecases.RetrieveTextFrom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PhotoState(
   val photoUri: String? = null,
   val recognizedText: String? = null
)
@HiltViewModel
class PhotoViewModel @Inject constructor(
   private val retrieveTextFrom: RetrieveTextFrom,
    // TODO: Inject observe all messages use case
): ViewModel() {

   private val _photoState = MutableStateFlow(PhotoState())
   val photoState = _photoState.asStateFlow()

   private val _events = Channel<Event>()
   val events = _events.receiveAsFlow()

   fun setPhotoUri(photoUri: String) {
      _photoState.update { it.copy(photoUri = photoUri) }
   }

   fun getRecognizedText(stringUri: String) {
      viewModelScope.launch(Dispatchers.IO) {
         val text = retrieveTextFrom(imageUri = stringUri)
         if (text != null) {
            _photoState.update { it.copy(recognizedText = text) }
         } else {
            _events.send(Event.ShowMessage("It was not possible to read the image"))
         }
      }
   }

   sealed class Event{
      data class ShowMessage(val message: String): Event()
   }
}