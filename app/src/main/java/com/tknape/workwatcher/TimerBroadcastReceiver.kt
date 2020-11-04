package com.tknape.workwatcher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tknape.workwatcher.di.DaggerClockComponent
import javax.inject.Inject

class TimerBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var viewModel: ClockViewModel

    override fun onReceive(context: Context?, intent: Intent?) {

        val application = context!!.applicationContext as WorkWatcherApp

        val appComponent = application.appComponent

        DaggerClockComponent.builder()
            .appComponent(appComponent)
            .build()
            .inject(this)

        if (intent != null && intent.action != null) {

            when (intent.action) {
                Commons.STOP_TIMER_ACTION -> viewModel.stopClock()
                Commons.START_PAUSE_TIMER_ACTION -> viewModel.startPauseClock()
                Commons.SKIP_TIMER_ACTION -> viewModel.skipToNextSession()
            }
        }
    }
}