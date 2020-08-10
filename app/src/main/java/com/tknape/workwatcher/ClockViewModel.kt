package com.tknape.workwatcher

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ClockViewModel: ViewModel() {

    private val mutableTimeLeftInMillis: MutableLiveData<Long> by lazy {  // TODO encapsulate
        MutableLiveData<Long>()
    }

    val timeLeftInMillis = mutableTimeLeftInMillis


    lateinit var countDownTimer: CountDownTimer
    var timerRunning = false
    private var isTimerInitialized = false

    fun startStop() {
        if (isTimerInitialized) {
            stopTimer()
        }
        else {
            startTimer()
        }
    }

    private fun startTimer() {
        mutableTimeLeftInMillis.value = 18000000L
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