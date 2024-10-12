package com.example.audioPlayer

expect class AudioPlayer {
    fun playSound(uri: String)
    fun release()
}

expect class AudioPlayerFactory() {
    fun create(): AudioPlayer
}