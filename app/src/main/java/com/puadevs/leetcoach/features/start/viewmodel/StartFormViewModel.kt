package com.puadevs.leetcoach.features.start.viewmodel

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class StartFormState(
    val problemNumber: String = ""
)

data class StartFormError(
    val problemNumber: String? = null
)

@HiltViewModel
class StartFormViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(StartFormState())
    val state = _state.asStateFlow()

    private val _error = MutableStateFlow(StartFormError())
    val error = _error.asStateFlow()


    fun setProblemNumber(text: String) {
        _state.update { it.copy(problemNumber = text) }
        _error.update { it.copy(problemNumber = null) }
    }

    fun validateProblemNumber(): String? {
        val problemNumber = _state.value.problemNumber
        return if (problemNumber.isBlank()) {
            "Enter a number"
        } else if (!problemNumber.isDigitsOnly()) {
            "Enter a valid number"
        } else null
    }

    fun submit(onValid: (Int) -> Unit) {
        val problemNumberError = validateProblemNumber()
        if (problemNumberError != null) {
            _error.update { it.copy(problemNumber = problemNumberError) }
            return
        }

        onValid(_state.value.problemNumber.toInt())
    }
}