package com.example.composeApp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.viento.allegrissimo.app.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Allegrissimo",
    ) {
        App()
    }
}