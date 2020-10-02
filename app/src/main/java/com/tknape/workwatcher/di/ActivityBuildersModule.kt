package com.tknape.workwatcher.di

import android.app.Activity
import com.tknape.workwatcher.Clock.Clock
import com.tknape.workwatcher.ClockFragment
import com.tknape.workwatcher.ClockViewModel
import com.tknape.workwatcher.MainActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun contributeClockViewModel() : ClockViewModel

    @ContributesAndroidInjector
    abstract fun contributeClockFragment() : ClockFragment


}