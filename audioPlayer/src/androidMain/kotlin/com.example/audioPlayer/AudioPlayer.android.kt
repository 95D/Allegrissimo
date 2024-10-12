package com.example.audioPlayer

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import org.koin.java.KoinJavaComponent.inject

actual class AudioPlayer(private val context: Context) {
    private val mediaPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    init {
        mediaPlayer.prepare()
    }

    actual fun playSound(uri: String) {
        mediaPlayer.setMediaItem(MediaItem.fromUri(uri))
        mediaPlayer.play()
    }

    actual fun release() {
        mediaPlayer.release()
    }
}

actual class AudioPlayerFactory {
    private val appContext: Context by inject(Context::class.java)
    actual fun create(): AudioPlayer = AudioPlayer(context = appContext)
}