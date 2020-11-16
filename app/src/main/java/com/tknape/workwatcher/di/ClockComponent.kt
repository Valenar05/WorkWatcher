package com.tknape.workwatcher.di

import com.tknape.workwatcher.*
import com.tknape.workwatcher.clock.Clock
import dagger.BindsInstance
import dagger.Component

@ClockScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ClockModule::class]
)
interface ClockComponent {
    fun inject(clockFragment: ClockFragment)
    fun inject(timerBroadcastReceiver: TimerBroadcastReceiver)
    fun inject(clockViewModel: ClockViewModel)
    fun inject(clock: Clock)
    fun application(): WorkWatcherApp

    @Component.Builder
    interface Builder {

         fun appComponent(appComponent: AppComponent): Builder

        fun build(): ClockComponent
    }
}