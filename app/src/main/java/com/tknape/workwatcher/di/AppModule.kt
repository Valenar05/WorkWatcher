package com.tknape.workwatcher.di

import com.tknape.workwatcher.Clock.Clock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

//    @Provides
//    fun giveString() : String {
//        return "meh"
//    }

    @Singleton
    @Provides
    fun provideClock(): Clock {
        return Clock()
    }
}