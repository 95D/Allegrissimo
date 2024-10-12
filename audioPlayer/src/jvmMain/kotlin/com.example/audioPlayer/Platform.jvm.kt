package com.example.audioPlayer

actual class Platform actual constructor() {
    actual val name: String
        get() = "Java ${System.getProperty("java.version")}"

    actual fun printLogD(tag: String, message: String) {
        println("$tag: $message")
    }
}