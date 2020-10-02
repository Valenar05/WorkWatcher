package com.tknape.workwatcher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tknape.workwatcher.Clock.Clock

class TimerBroadcastReceiver() : BroadcastReceiver() {

    val clock = Clock()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.action != null) {

            when (intent.action) {
                Commons.STOP_TIMER_ACTION -> clock.stopClock()
                Commons.START_PAUSE_TIMER_ACTION -> clock.startPauseClock()
                Commons.SKIP_TIMER_ACTION -> clock.skipToNextSession()
            }
        }
    }
}