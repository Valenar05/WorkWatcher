package com.tknape.workwatcher

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import kotlin.concurrent.timer

class ClockViewModel: ViewModel() {

    private val mutableTimeLeftInMillis: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }

    val timeLeftInMillis : LiveData<String> = Transformations.map(mutableTimeLeftInMillis) { time ->
        "${if (time / 60000 < 10) {"0"} else {""}}${time / 60000}:${if((time % 60000) / 1000 < 10) {"0"} else {""}}${(time % 60000) / 1000}" //TODO make string formatting more readable
    }

    lateinit var countDownTimer: CountDownTimer
    var timerRunning = false
    private var isTimerInitialized = false

    fun startPauseClock() {

        if (!timerRunning && isTimerInitialized ) {
            resumeTimer()
            Log.d("Timer", "Resuming timer")
        }

        else if (timerRunning && isTimerInitialized) {
            pauseTimer()
            Log.d("Timer", "Stopping timer")
            Log.d("Timer", mutableTimeLeftInMillis.value.toString())
        }

        else {
            startTimer()
            Log.d("Timer", "Starting timer")
            Log.d("Timer", mutableTimeLeftInMillis.value.toString())

        }
    }

    fun stopClock() {
        stopTimer()
    }

    private fun createTimer(): CountDownTimer {
        countDownTimer = object: CountDownTimer(mutableTimeLeftInMillis.value!!, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                mutableTimeLeftInMillis.value = millisUntilFinished
            }
            override fun onFinish() {
            }
        }
        return countDownTimer
    }

    private fun startTimer() {
        mutableTimeLeftInMillis.value = 1500000L
        createTimer().start()

        isTimerInitialized = true
        timerRunning = true
    }

    private fun resumeTimer() {
        createTimer().start()
        timerRunning = true
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        timerRunning = false
    }

    private fun stopTimer() {
        countDownTimer.cancel()
        isTimerInitialized = false
        timerRunning = false
    }

}