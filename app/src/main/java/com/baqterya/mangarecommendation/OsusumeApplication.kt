package com.baqterya.mangarecommendation

import android.app.Application
import timber.log.Timber

class OsusumeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}