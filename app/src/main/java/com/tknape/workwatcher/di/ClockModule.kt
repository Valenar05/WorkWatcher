package com.tknape.workwatcher.di

import com.tknape.workwatcher.WorkWatcherApp
import com.tknape.workwatcher.clock.Clock
import com.tknape.workwatcher.clock.CycleHandler
import dagger.Module
import dagger.Provides

@Module
class ClockModule {

    @ClockScope
    @Provides
    fun provideClock(application: WorkWatcherApp, cycleHandler: CycleHandler) = Clock(application, cycleHandler)

}
