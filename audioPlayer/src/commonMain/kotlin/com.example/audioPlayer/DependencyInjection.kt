package com.example.audioPlayer

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val platformModule = module {
    singleOf(::Platform)
    singleOf(::AudioRepository)
}
