package com.linroid.klipperx

import MainView
import android.content.res.Configuration
import android.os.Bundle
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent

class MainActivity : PreComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.statusBarColor = 0xff4caf50.toInt()
        window?.navigationBarColor  = 0xff4caf50.toInt()
        setContent {
            MainView()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateDarkMode()
    }
}