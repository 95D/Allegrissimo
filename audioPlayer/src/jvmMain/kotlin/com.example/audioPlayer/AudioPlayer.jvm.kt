package com.example.audioPlayer

import java.io.File
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.DataLine
import javax.sound.sampled.LineEvent
import javax.sound.sampled.LineListener


actual class AudioPlayer(private val onEndAudio: () -> Unit) {
    private var audioClip: Clip? = null
    private var audioStream: AudioInputStream? = null
    private val listener: LineListener = LineListener { event ->
        if (event.type == LineEvent.Type.STOP) {
            onEndAudio()
        }
    }

    actual fun playSound(uri: String) {
        println(uri)
        val audioStream = AudioSystem.getAudioInputStream(File(uri))
        this.audioStream = audioStream
        val audioFormat = audioStream.format
        val info = DataLine.Info(Clip::class.java, audioFormat)
        audioClip = (AudioSystem.getLine(info) as Clip).also {
            it.addLineListener(listener)
            it.open(audioStream)
            it.loop(Clip.LOOP_CONTINUOUSLY)
            it.start()
        }
    }

    actual fun release() {
        audioClip?.close();
        audioStream?.close();
    }
}

actual class AudioPlayerFactory {
    actual fun create(onEndAudio: () -> Unit): AudioPlayer =
        AudioPlayer(onEndAudio)
}