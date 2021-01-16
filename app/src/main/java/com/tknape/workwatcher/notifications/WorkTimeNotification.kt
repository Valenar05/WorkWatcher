package com.tknape.workwatcher.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.tknape.workwatcher.Commons
import com.tknape.workwatcher.R
import com.tknape.workwatcher.Receiver.WorkTimeBroadcastReceiver

class WorkTimeNotification(context: Context): BaseNotification(context) {

    private val startTimerIntent = Intent(
        context, WorkTimeBroadcastReceiver::class.java).setAction(Commons.WORK_TIME_START_TIMER_ACTION)

    private val startTimerPendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, startTimerIntent, 0)

    override fun build(title: String, text: String, channel: String): BaseNotification {
        super.build(title, text, channel)
        notificationBuilder
            .addAction(R.drawable.ic_stop, context.getString(R.string.start_timer), startTimerPendingIntent)
        return this
    }
}