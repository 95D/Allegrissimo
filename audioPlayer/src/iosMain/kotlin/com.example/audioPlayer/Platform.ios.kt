package com.example.audioPlayer

import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val name: String
        get() = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

    actual fun printLogD(tag: String, message: String) {
        println("$tag: $message")
    }
}