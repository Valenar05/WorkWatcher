package com.tknape.workwatcher.di

import com.tknape.workwatcher.Clock.Clock
import com.tknape.workwatcher.ClockFragment
import com.tknape.workwatcher.ClockViewModel
import com.tknape.workwatcher.TimerBroadcastReceiver
import com.tknape.workwatcher.WorkWatcherApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector


@ApplicationScope
@Component(
    modules = [AppModule::class]
)
interface AppComponent : AndroidInjector<WorkWatcherApp> {
    fun inject(clockFragment: ClockFragment)
    fun inject(timerBroadcastReceiver: TimerBroadcastReceiver)
    fun inject(clockViewModel: ClockViewModel)

    @Component.Builder
    interface Builder {

        fun build() : AppComponent

        @BindsInstance
        fun applicationBind(application: WorkWatcherApp) : Builder

    }
}