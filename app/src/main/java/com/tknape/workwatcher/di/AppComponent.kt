package com.tknape.workwatcher.di

import com.tknape.workwatcher.Clock.Clock
import com.tknape.workwatcher.TimerBroadcastReceiver
import com.tknape.workwatcher.WorkWatcherApp
import dagger.Component


@ApplicationScope
@Component(
    modules = [AppModule::class]
)
interface AppComponent {
    fun clock(): Clock
    fun application(): WorkWatcherApp
    fun inject(application: WorkWatcherApp)
}