package com.linroid.klipperx

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        koinContext = this
        startKlipperX()
    }
}