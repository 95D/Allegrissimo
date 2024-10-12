package com.example.audioPlayer

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

expect class Platform() {
    val name: String
    fun printLogD(tag: String, message: String)
}


