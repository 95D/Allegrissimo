package com.example.audioPlayer

import android.content.Context
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import org.koin.java.KoinJavaComponent.inject

actual class AudioPlayer(
    context: Context,
    private val onEndAudio: () -> Unit
) {
    private val listener: Player.Listener = object : Player.Listener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            super.onMediaItemTransition(mediaItem, reason)
            Log.d("Player listener", "State changed $reason")
            if (reason == Player.MEDIA_ITEM_TRANSITION_REASON_REPEAT) {
                onEndAudio()
            }
        }
    }

    private val mediaPlayer: ExoPlayer = ExoPlayer.Builder(context).build()


    actual fun playSound(uri: String) {
        if (mediaPlayer.isPlaying) return
        mediaPlayer.prepare()
        mediaPlayer.setMediaItem(MediaItem.fromUri(uri))
        mediaPlayer.repeatMode = Player.REPEAT_MODE_ONE
        mediaPlayer.play()
        mediaPlayer.addListener(listener)
    }

    actual fun release() {
        mediaPlayer.stop()
    }
}

actual class AudioPlayerFactory {
    private val appContext: Context by inject(Context::class.java)
    actual fun create(onEndAudio: () -> Unit): AudioPlayer =
        AudioPlayer(context = appContext, onEndAudio = onEndAudio)
}