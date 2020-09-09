package com.tknape.workwatcher

import androidx.lifecycle.LiveData


interface IClockViewModel {

    val timeLeftInMillis: LiveData<Long>

    fun startPauseClock()

    fun stopClock()

    fun setTimeLeftInMillis(time: Long)

    fun isTimerRunning(): Boolean

    fun skipToNextSession()


}