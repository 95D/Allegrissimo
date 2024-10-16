package com.viento.allegrissimo.tempo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audioPlayer.AudioPlayer
import com.example.audioPlayer.AudioPlayerFactory
import com.example.audioPlayer.AudioRepository
import com.viento.allegrissimo.tempo.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TempoScreenViewModel : ViewModel() {
    private val audioPlayer: AudioPlayer = AudioPlayerFactory().create {
        val currentIndex = currentNoteIndexMutableStateFlow.value
        currentNoteIndexMutableStateFlow.value =
            if (currentIndex >= noteCountStateFlow.value - 1) {
                0
            } else {
                currentIndex + 1
            }
    }

    private val bpmMutableStateFlow: MutableStateFlow<Int> = MutableStateFlow(60)
    val bpmStateFlow: StateFlow<Int> = bpmMutableStateFlow

    private val noteMutableStateFlow: MutableStateFlow<Note> = MutableStateFlow(Note.QUARTER)
    val noteStateFlow: StateFlow<Note> = noteMutableStateFlow

    private val noteCountMutableStateFlow: MutableStateFlow<Int> = MutableStateFlow(4)
    val noteCountStateFlow: StateFlow<Int> = noteCountMutableStateFlow

    private val isPlayingMutableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isPlayingStateFlow: StateFlow<Boolean> = isPlayingMutableStateFlow

    private val currentNoteIndexMutableStateFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    val currentNoteIndexStateFlow: StateFlow<Int> = currentNoteIndexMutableStateFlow

    private suspend fun play() = withContext(Dispatchers.Unconfined) {
        val bpm = bpmStateFlow.value
        val noteLength = noteStateFlow.value.length
        val noteCount = noteCountStateFlow.value
        val fileName =
            resolveBeepFile(bpm = bpm, noteLength = noteLength, noteCount = noteCount)
        val (header, data) = TempoGenerator()
            .generateWaveBuffer(bpm = bpm, noteLength = noteLength)
        AudioRepository().saveAudioFile(fileName, header + data)
        val uri = AudioRepository().getFilePath(fileName)
        withContext(Dispatchers.Main) {
            audioPlayer.playSound(uri)
        }
    }

    private suspend fun stop() = withContext(Dispatchers.Main) {
        currentNoteIndexMutableStateFlow.value = 0
        audioPlayer.release()
    }

    private fun resolveBeepFile(bpm: Int, noteLength: Float, noteCount: Int): String =
        "beep_${bpm}_${noteLength}_${noteCount}.wav"

    fun upBpm() {
        if (bpmStateFlow.value >= 200) {
            return
        }
        bpmMutableStateFlow.value += 10
        mayReplayWithNewState()
    }

    fun downBpm() {
        if (bpmStateFlow.value <= 30) {
            return
        }
        bpmMutableStateFlow.value -= 10
        mayReplayWithNewState()
    }

    fun upNote() {
        noteMutableStateFlow.value = noteStateFlow.value.up()
        mayReplayWithNewState()
    }

    fun downNote() {
        noteMutableStateFlow.value = noteStateFlow.value.down()
        val currentDivided = noteStateFlow.value.divided
        val currentNoteCount = noteCountStateFlow.value
        if (currentNoteCount > currentDivided) {
            noteCountMutableStateFlow.value = currentDivided
        }
        mayReplayWithNewState()
    }

    fun upNoteCount() {
        val currentDivided = noteStateFlow.value.divided
        val currentNoteCount = noteCountStateFlow.value
        if (currentNoteCount == currentDivided) return
        noteCountMutableStateFlow.value = currentNoteCount * 2
        mayReplayWithNewState()
    }

    fun downNoteCount() {
        if (noteCountStateFlow.value == 1) return
        noteCountMutableStateFlow.value /= 2
        mayReplayWithNewState()
    }

    fun togglePlay() {
        isPlayingMutableStateFlow.value = !isPlayingStateFlow.value
        viewModelScope.launch {
            if (isPlayingStateFlow.value) {
                play()
            } else {
                stop()
            }
        }
    }

    private fun mayReplayWithNewState() {
        if (!isPlayingStateFlow.value) return
        viewModelScope.launch {
            stop()
            play()
        }
    }
}