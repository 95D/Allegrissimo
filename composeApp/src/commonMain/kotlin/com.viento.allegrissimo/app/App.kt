package com.viento.allegrissimo.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import allegrissimo.composeapp.generated.resources.Res
import allegrissimo.composeapp.generated.resources.compose_multiplatform
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.ui.text.input.KeyboardType

@Composable
@Preview
fun App() {
    MaterialTheme {
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
                    onClick = {
                        isPlaying = !isPlaying

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
}