package com.uvg.lab6pokemon

import android.app.Application
import com.uvg.lab6pokemon.di.appModule
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}
