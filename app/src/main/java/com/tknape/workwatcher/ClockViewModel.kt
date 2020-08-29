package com.tknape.workwatcher

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tknape.workwatcher.Clock.Clock

class ClockViewModel : ViewModel() {

    private lateinit var clock: Clock
    private val mutableTimeLeftInMillis: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

    val timeLeftInMillis: LiveData<Long> = mutableTimeLeftInMillis

    val timerProgressInPercents: LiveData<Float> =
        Transformations.map(mutableTimeLeftInMillis) { timeLeft ->
            (100 - (timeLeft.toFloat() / Clock.workPeriodInMillis.toFloat() * 100))
        }

    val formattedTimeLeftInMillis : LiveData<String> = Transformations.map(mutableTimeLeftInMillis) { time ->
        "${if (time / 60000 < 10) {"0"} else {""}}${time / 60000}:${if((time % 60000) / 1000 < 10) {"0"} else {""}}${(time % 60000) / 1000}" //TODO make string formatting more readable
    }

    fun startPauseClock() {

        if (!this::clock.isInitialized) {
            clock = Clock(this)
        }

        if (!Clock.timerRunning && Clock.isTimerInitialized) {
            clock.resumeTimer()
            Log.d("Timer", "Resuming timer")
        }
        else if (Clock.timerRunning && Clock.isTimerInitialized) {
            clock.pauseTimer()
            Log.d("Timer", "Stopping timer")
            Log.d("Timer", mutableTimeLeftInMillis.value.toString())
        }
        else {
            clock.startTimer()
            Log.d("Timer", "Starting timer")
            Log.d("Timer", mutableTimeLeftInMillis.value.toString())
        }
    }

    fun stopClock() {
        clock.stopTimer()
    }

    fun setTimeLeftInMillis(time: Long) {
        mutableTimeLeftInMillis.value = time
    }

    fun isTimerRunning(): Boolean {
        return Clock.timerRunning
    }
}