package com.tknape.workwatcher

import android.app.Application
import android.util.Log
import com.tknape.workwatcher.di.ApplicationScope
import com.tknape.workwatcher.di.DaggerAppComponent

@ApplicationScope
class WorkWatcherApp: Application() {

    val appComponent = DaggerAppComponent.create()

    init {
        Log.d("WorkWatcherApp", "Starting app")
    }
}