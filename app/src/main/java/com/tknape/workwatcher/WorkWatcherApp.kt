package com.tknape.workwatcher

import android.app.Application
import android.util.Log
import com.tknape.workwatcher.di.AppComponent
import com.tknape.workwatcher.di.ApplicationScope
import com.tknape.workwatcher.di.DaggerAppComponent

@ApplicationScope
class WorkWatcherApp: Application() {

    init {
        Log.d("WorkWatcherApp", "Starting app")
    }
}