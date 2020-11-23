package com.tknape.workwatcher.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tknape.workwatcher.MainActivity
import com.tknape.workwatcher.R

abstract class BaseNotification(private val context: Context) {

    private val notificationManager = NotificationManagerCompat.from(context)

    lateinit var notificationBuilder: NotificationCompat.Builder


    private val intent = Intent(context, MainActivity::class.java)

    private val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

    open fun build(title: String, text: String, channel: String): BaseNotification {
        notificationBuilder = NotificationCompat.Builder(context, channel)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setNotificationSilent()

        return this
    }

    fun send() {
        notificationManager.notify(1, notificationBuilder.build()) // TODO save ID
    }

}