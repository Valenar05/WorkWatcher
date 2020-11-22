package com.tknape.workwatcher

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import javax.inject.Inject

class VibrationHandler(val application: WorkWatcherApp) {

    fun vibrate() {
        val v = application.applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v!!.vibrate(
                VibrationEffect.createOneShot(
                    500,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            //deprecated in API 26
            v!!.vibrate(500)
        }
    }
}