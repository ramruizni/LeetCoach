package com.puadevs.leetcoach.features.start.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.puadevs.leetcoach.features.start.viewmodel.StartFormViewModel
import com.puadevs.leetcoach.features.start.viewmodel.StartViewModel
import com.puadevs.leetcoach.ui.composable.BoxLoadingIndicator

@Composable
fun StartScreen(
    viewModel: StartViewModel = viewModel(),
    formViewModel: StartFormViewModel = viewModel()
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()
    val formState by formViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(context) {
        viewModel.events.collect { event ->
            when (event) {
                is StartViewModel.Event.NavigateToChat -> {

                }

                is StartViewModel.Event.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Enter problem number")
                Spacer(Modifier.height(12.dp))
                TextField(
                    value = formState.problemNumber,
                    onValueChange = { formViewModel.setProblemNumber(it) }
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        formViewModel.submit { validProblemNumber ->
                            viewModel.start(problemNumber = validProblemNumber)
                        }
                    }
                ) {
                    Text("Start")
                }
            }
        }
        BoxLoadingIndicator(isLoading = state.isLoading)
    }
}