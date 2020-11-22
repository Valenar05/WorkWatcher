package com.tknape.workwatcher.di

import com.tknape.workwatcher.*
import com.tknape.workwatcher.clock.CycleHandler
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @ApplicationScope
    @Provides
    fun provideAlarmHandler(application: WorkWatcherApp) = AlarmHandler(application)

    @ApplicationScope
    @Provides
    fun provideCycleHandler(application: WorkWatcherApp) = CycleHandler(application)

    @ApplicationScope
    @Provides
    fun provideClockViewModel(application: WorkWatcherApp) = ClockViewModel(application)

    @ApplicationScope
    @Provides
    fun provideVibrationHandler(application: WorkWatcherApp) = VibrationHandler(application)
}
