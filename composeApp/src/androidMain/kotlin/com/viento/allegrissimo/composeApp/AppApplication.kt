package com.viento.allegrissimo.composeApp

import android.app.Application
import com.example.audioPlayer.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            platformModule
        }
    }
}