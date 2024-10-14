package com.example.audioPlayer

import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioPlayerDelegateProtocol
import platform.Foundation.NSURL
import platform.darwin.NSObject

actual class AudioPlayer(onEndAudio: () -> Unit) {
    private var avAudioPlayer: AVAudioPlayer? = null

    private val delegateProtocol: AVAudioPlayerDelegateProtocol =
        AVAudioPlayerDelegate(onEndAudio)

    @OptIn(ExperimentalForeignApi::class)
    actual fun playSound(uri: String) {
        if (avAudioPlayer != null) {
            return
        }
        val mediaItem = NSURL.URLWithString(uri) ?: return
        val avAudioPlayer = AVAudioPlayer(mediaItem, error = null)
        this.avAudioPlayer = avAudioPlayer
        avAudioPlayer.delegate = delegateProtocol
        avAudioPlayer.prepareToPlay()
        avAudioPlayer.numberOfLoops = -1
        avAudioPlayer.play()
    }

    actual fun release() {
        avAudioPlayer?.stop()
        avAudioPlayer = null
    }
}

actual class AudioPlayerFactory {
    actual fun create(onEndAudio: () -> Unit): AudioPlayer = AudioPlayer(onEndAudio)
}

class AVAudioPlayerDelegate(
    private val onEndAudio: () -> Unit
) : NSObject(), AVAudioPlayerDelegateProtocol {
    override fun audioPlayerEndInterruption(player: AVAudioPlayer) {
        onEndAudio()
    }
}
