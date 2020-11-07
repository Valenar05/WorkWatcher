package com.tknape.workwatcher.di

import com.tknape.workwatcher.clock.Clock
import com.tknape.workwatcher.ClockViewModel
import com.tknape.workwatcher.WorkWatcherApp
import dagger.Module
import dagger.Provides

@Module
class ClockModule {


    @ApplicationScope
    @Provides
    fun provideClock() = Clock()

    @ApplicationScope
    @Provides
    fun provideClockViewModel(application: WorkWatcherApp) = ClockViewModel(application)


}
