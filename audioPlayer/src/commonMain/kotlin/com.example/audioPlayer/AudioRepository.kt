package com.example.audioPlayer

expect class AudioRepository() {
    fun saveAudioFile(fileName: String, bytes: ByteArray)
    fun getFilePath(fileName: String): String
}