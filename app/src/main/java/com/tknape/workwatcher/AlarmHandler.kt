package com.tknape.workwatcher

import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri

class AlarmHandler(application: WorkWatcherApp) {
    private val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    private val ringtone: Ringtone = RingtoneManager.getRingtone(application, notification)

    fun playRingtone() {
        ringtone.play()
    }
}