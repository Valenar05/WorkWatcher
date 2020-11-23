package com.tknape.workwatcher.di

import com.tknape.workwatcher.*
import com.tknape.workwatcher.clock.CycleHandler
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector


@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        AndroidInjectionModule::class,
        ActivityBuildersModule::class]
)
interface AppComponent : AndroidInjector<WorkWatcherApp> {
    fun alarmHandler(): AlarmHandler
    fun cycleHandler(): CycleHandler
    fun clockViewModel(): ClockViewModel
    fun vibrationHander(): VibrationHandler
    fun application(): WorkWatcherApp
    fun inject(vibrationHandler: VibrationHandler)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: WorkWatcherApp): Builder

        fun build(): AppComponent

    }
}