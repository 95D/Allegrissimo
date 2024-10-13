package com.viento.allegrissimo.tempo.ui

import allegrissimo.composeapp.generated.resources.Res
import allegrissimo.composeapp.generated.resources.compose_multiplatform
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.viento.allegrissimo.tempo.TempoScreenViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun TempoScreen() {
    val viewModel = TempoScreenViewModel()
    var bpm by remember { mutableStateOf(70) }
    var isPlaying by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Text("BPM")
            TextField(
                value = bpm.toString(),
                onValueChange = { bpm = it.toInt() },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        Row {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    isPlaying = !isPlaying
                    if (isPlaying) {
                        viewModel.play()
                    } else {
                        viewModel.release()
                    }
                }
            ) {
                Text("Play")
            }
        }
        AnimatedVisibility(isPlaying) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Now playing!")
            }
        }
    }
}