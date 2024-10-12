package com.example.audioPlayer

actual class AudioPlayer {
    actual fun playSound(uri: String) {
        throw NotImplementedError("Not yet implemented")
    }

    actual fun release() {
    }
}