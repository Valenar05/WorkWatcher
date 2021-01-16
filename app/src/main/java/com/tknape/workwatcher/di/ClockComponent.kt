package com.tknape.workwatcher.di

import com.tknape.workwatcher.*
import com.tknape.workwatcher.Receiver.TimerBroadcastReceiver
import com.tknape.workwatcher.Receiver.WorkTimeBroadcastReceiver
import dagger.Component

@ClockScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ClockModule::class]
)
interface ClockComponent {
    fun inject(clockFragment: ClockFragment)
    fun inject(timerBroadcastReceiver: TimerBroadcastReceiver)
    fun inject(workTimeBroadcastReceiver: WorkTimeBroadcastReceiver)
    fun inject(clockViewModel: ClockViewModel)
    fun application(): WorkWatcherApp

    @Component.Builder
    interface Builder {

         fun appComponent(appComponent: AppComponent): Builder

        fun build(): ClockComponent
    }
}