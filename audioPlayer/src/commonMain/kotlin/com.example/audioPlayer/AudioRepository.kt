package com.example.audioPlayer

expect class AudioRepository() {
    fun saveAudioFile(fileName: String, bytes: ByteArray)
    fun getFileUri(fileName: String): String
}