package com.example.audioPlayer

import android.content.Context
import android.net.Uri
import org.koin.java.KoinJavaComponent.inject
import java.io.File

actual class AudioRepository {
    private val appContext: Context by inject(Context::class.java)

    private fun getAudioFile(fileName: String): File {
        val audioFile = File(appContext.filesDir, fileName)
        return audioFile
    }

    actual fun saveAudioFile(fileName: String, bytes: ByteArray) {
        val audioFile = getAudioFile(fileName)
        audioFile.writeBytes(bytes)
    }

    actual fun getFileUri(fileName: String): String =
        Uri.fromFile(getAudioFile(fileName)).toString()
}