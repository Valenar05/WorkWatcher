package com.tknape.workwatcher

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.tknape.workwatcher.notifications.BaseNotification

class TimerNotification(private val context: Context): BaseNotification(context) {

    private val stopIntent =
        Intent(context, TimerBroadcastReceiver::class.java).setAction(Commons.STOP_TIMER_ACTION)

    private val stopPendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, stopIntent, 0)

    private val startPauseIntent = Intent(
        context,
        TimerBroadcastReceiver::class.java
    ).setAction(Commons.START_PAUSE_TIMER_ACTION)

    private val startPausePendingIntent: PendingIntent =
        PendingIntent.getBroadcast(context, 0, startPauseIntent, 0)

    private val skipIntent =
        Intent(context, TimerBroadcastReceiver::class.java).setAction(Commons.SKIP_TIMER_ACTION)

    private val skipPendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, skipIntent, 0)


    override fun build(title: String, text: String, channel: String): BaseNotification {
        super.build(title, text, channel)
                notificationBuilder
            .addAction(R.drawable.ic_stop, context.getString(R.string.stop), stopPendingIntent)
            .addAction(R.drawable.ic_stop, context.getString(R.string.start), startPausePendingIntent)
            .addAction(R.drawable.ic_stop, context.getString(R.string.skip), skipPendingIntent)

        return this
    }

//    fun cancelNotification() {
//        notificationManager.cancel(1)
//    }
}