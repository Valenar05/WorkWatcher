package com.tknape.workwatcher.di

import android.app.Application
import com.tknape.workwatcher.Clock.Clock
import com.tknape.workwatcher.WorkWatcherApp
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    @ApplicationScope
    fun provideClock() = Clock()

    @Provides
    @ApplicationScope
    fun provideApplication() = WorkWatcherApp()
}