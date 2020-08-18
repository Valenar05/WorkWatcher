package com.tknape.workwatcher

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

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

    fun startStop() {
        if (isTimerInitialized) {
            stopTimer()
            Log.d("Timer", "Stopping timer")
            Log.d("Timer", mutableTimeLeftInMillis.value.toString())

        }
        else {
            startTimer()
            Log.d("Timer", "Starting timer")
            Log.d("Timer", mutableTimeLeftInMillis.value.toString())

        }
    }

    private fun startTimer() {
        mutableTimeLeftInMillis.value = 1500000L
        countDownTimer = object: CountDownTimer(mutableTimeLeftInMillis.value!!, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                mutableTimeLeftInMillis.value = millisUntilFinished

            }

            override fun onFinish() {
            }
        }.start()
        isTimerInitialized = true
        timerRunning = true
    }

    private fun stopTimer() {
        countDownTimer.cancel()
        isTimerInitialized = false
        timerRunning = false
    }

}