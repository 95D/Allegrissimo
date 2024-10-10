package com.example.audioPlayer

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


