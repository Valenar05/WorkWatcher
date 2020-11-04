package com.tknape.workwatcher

import android.util.Log
import com.tknape.workwatcher.di.AppComponent
import com.tknape.workwatcher.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class WorkWatcherApp: DaggerApplication() {

    val appComponent: AppComponent = DaggerAppComponent
        .builder()
        .application(this)
        .build()

    init {
        Log.d("WorkWatcherApp", "Starting app")
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}