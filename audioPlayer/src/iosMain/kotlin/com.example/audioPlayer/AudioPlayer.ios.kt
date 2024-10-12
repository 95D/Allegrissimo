package com.example.audioPlayer

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.Foundation.NSURL

actual class AudioPlayer {

    @OptIn(ExperimentalForeignApi::class)
    actual fun playSound(uri: String) {
        val mediaItem = NSURL.URLWithString(uri) ?: return
        val avAudioPlayer = AVAudioPlayer(mediaItem, error = null)
        avAudioPlayer.prepareToPlay()
        avAudioPlayer.play()
    }

    actual fun release() {
    }
}

actual class AudioPlayerFactory {
    actual fun create(): AudioPlayer = AudioPlayer()
}