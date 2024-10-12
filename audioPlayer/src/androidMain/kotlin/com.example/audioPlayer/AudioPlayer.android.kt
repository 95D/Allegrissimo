package com.example.audioPlayer

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

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