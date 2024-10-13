package com.viento.allegrissimo.tempo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioPlayer.AudioPlayer
import com.example.audioPlayer.AudioPlayerFactory
import com.example.audioPlayer.AudioRepository
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi

class TempoScreenViewModel : ViewModel() {
    private val audioPlayer: AudioPlayer = AudioPlayerFactory().create()

    fun play(bpm: Int, tone: Int, beat: Int) {
        viewModelScope.launch {
            val fileName = resolveBeepFile(bpm = bpm, tone = tone, beat = beat)
            val (header, data) = TempoGenerator()
                .generateWaveBuffer(bpm = bpm, tone = tone, beat = beat)
            AudioRepository().saveAudioFile(fileName, header + data)
            val uri = AudioRepository().getFileUri(fileName)
            audioPlayer.playSound(uri)
        }
    }

    private fun resolveBeepFile(bpm: Int, tone: Int, beat: Int): String =
        "beep_${bpm}_${tone}_${tone}.wav"

    fun release() {
        audioPlayer.release()
    }
}