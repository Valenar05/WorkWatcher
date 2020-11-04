package com.tknape.workwatcher.di

import com.tknape.workwatcher.*
import dagger.Component

@ClockScope
@Component(
    dependencies = [AppComponent::class]
)
interface ClockComponent {
    fun inject(clockFragment: ClockFragment)
    fun inject(timerBroadcastReceiver: TimerBroadcastReceiver)
    fun inject(clockViewModel: ClockViewModel)

    @Component.Builder
    interface Builder {

        fun appComponent(appComponent: AppComponent): Builder

        fun build(): ClockComponent
    }
}