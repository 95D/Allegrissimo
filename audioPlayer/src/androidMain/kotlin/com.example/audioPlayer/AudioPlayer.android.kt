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
        if (mediaPlayer.isPlaying) return
        mediaPlayer.prepare()
        mediaPlayer.setMediaItem(MediaItem.fromUri(uri))
        mediaPlayer.repeatMode = Player.REPEAT_MODE_ONE
        mediaPlayer.play()
    }

    actual fun release() {
        mediaPlayer.stop()
    }
}

actual class AudioPlayerFactory {
    private val appContext: Context by inject(Context::class.java)
    actual fun create(): AudioPlayer = AudioPlayer(context = appContext)
}