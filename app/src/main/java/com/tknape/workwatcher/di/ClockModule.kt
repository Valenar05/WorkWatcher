package com.tknape.workwatcher.di

import com.tknape.workwatcher.Clock.Clock
import dagger.Module
import dagger.Provides

@Module
class ClockModule {
    @Provides
    @ClockScope
    fun provideClock() = Clock()
}