package com.tknape.workwatcher.di

import com.tknape.workwatcher.ClockFragment
import com.tknape.workwatcher.ClockViewModel
import com.tknape.workwatcher.TimerBroadcastReceiver
import com.tknape.workwatcher.WorkWatcherApp
import dagger.Component

@ClockScope
@Component(
    dependencies = [AppComponent::class]
)
interface ClockComponent {
    fun inject(clockFragment: ClockFragment)
    fun inject(timerBroadcastReceiver: TimerBroadcastReceiver)
    fun inject(clockViewModel: ClockViewModel)
    fun inject(application: WorkWatcherApp)
}