package com.example.audioPlayer

import android.os.Build
import android.util.Log

actual class Platform actual constructor() {
    actual val name: String
        get() = "Android ${Build.VERSION.SDK_INT}"

    actual fun printLogD(tag: String, message: String) {
        Log.d(tag, message)
    }
}