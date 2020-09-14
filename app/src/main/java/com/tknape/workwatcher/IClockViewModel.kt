package com.tknape.workwatcher



interface IClockViewModel {

    fun startPauseClock()

    fun stopClock()

    fun isTimerRunning(): Boolean

    fun skipToNextSession()


}