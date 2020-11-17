package com.tknape.workwatcher

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class TimerNotification(private val context: Context) {


    private val notificationManager = NotificationManagerCompat.from(context)

    private val intent = Intent(context, MainActivity::class.java)

    private val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

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


    fun sendNotification(timeLeftInSession: String, sessionType: String) {
        val notificationBuilder = NotificationCompat.Builder(context, "CHANNEL_1")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(timeLeftInSession)
            .setContentText(sessionType)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setNotificationSilent()
            .addAction(R.drawable.ic_stop, "Stop", stopPendingIntent)
            .addAction(R.drawable.ic_stop, "Start", startPausePendingIntent)
            .addAction(R.drawable.ic_stop, "Skip", skipPendingIntent)

        notificationManager.notify(1, notificationBuilder.build()) // TODO save ID
    }

//    fun cancelNotification() {
//        notificationManager.cancel(1)
//    }
}