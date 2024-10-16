package com.example.audioPlayer

import java.io.File
import java.net.URI

actual class AudioRepository {
    private fun getAudioDirectory(): File =
        File("/Users/user/desktop/", DIR_AUDIO)

    private fun getAudioFile(fileName: String): File {
        val audioDirectory = getAudioDirectory()
        val audioFile = File(audioDirectory.absolutePath, fileName)
        return audioFile
    }

    actual fun saveAudioFile(fileName: String, bytes: ByteArray) {
        val audioFile = getAudioFile(fileName)
        audioFile.writeBytes(bytes)
    }

    actual fun getFilePath(fileName: String): String =
        URI.create(getAudioFile(fileName).absolutePath).toString()

    companion object {
        private const val DIR_AUDIO = "Audio"
    }
}