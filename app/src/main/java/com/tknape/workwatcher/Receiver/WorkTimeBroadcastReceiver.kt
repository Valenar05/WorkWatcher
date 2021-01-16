package com.tknape.workwatcher.Receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tknape.workwatcher.ClockViewModel
import com.tknape.workwatcher.Commons
import com.tknape.workwatcher.WorkWatcherApp
import com.tknape.workwatcher.di.DaggerClockComponent
import javax.inject.Inject

class WorkTimeBroadcastReceiver: BroadcastReceiver() {

    @Inject
    lateinit var viewModel: ClockViewModel

    override fun onReceive(context: Context?, intent: Intent?) {

        val appComponent = (context!!.applicationContext as WorkWatcherApp).appComponent

            DaggerClockComponent.builder()
            .appComponent(appComponent)
            .build()
            .inject(this)

        if (intent != null && intent.action != null) {

            when (intent.action) {
                Commons.WORK_TIME_START_TIMER_ACTION -> viewModel.startPauseClock()
            }
        }
    }
}