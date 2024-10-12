package com.example.audioPlayer

actual class Platform actual constructor() {
    actual val name: String
        get() = "Web with Kotlin/Wasm"

    actual fun printLogD(tag: String, message: String) {
        println("$tag: $message")
    }
}