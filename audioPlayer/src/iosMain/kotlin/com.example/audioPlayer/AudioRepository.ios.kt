package com.example.audioPlayer

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSUserDomainMask
import platform.Foundation.create
import platform.Foundation.stringByAppendingPathComponent
import platform.Foundation.writeToFile

actual class AudioRepository {
    private fun getAudioFilePath(fileName: String): String {
        val documentDirectory = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        )[0] as NSString

        // Append the file name to the document directory path
        val filePath = documentDirectory.stringByAppendingPathComponent(fileName)
        return filePath
    }

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    actual fun saveAudioFile(fileName: String, bytes: ByteArray) {
        val audioFilePath = getAudioFilePath(fileName)
        memScoped {
            NSData.create(bytes = allocArrayOf(bytes), length = bytes.size.toULong())
                .writeToFile(path = audioFilePath, atomically = true)
        }
    }

    actual fun getFileUri(fileName: String): String =
        getAudioFilePath(fileName)

    companion object {
        private const val DIR_AUDIO = "Audio"
    }
}