package com.tknape.workwatcher.Clock

import android.os.CountDownTimer
import com.tknape.workwatcher.ClockViewModel

class Clock(val viewModel: ClockViewModel) {



    lateinit var countDownTimer: CountDownTimer

    private fun createTimer(): CountDownTimer {
        countDownTimer = object : CountDownTimer(viewModel.timeLeftInMillis.value!!, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                viewModel.setTimeLeftInMillis(millisUntilFinished)
            }

            override fun onFinish() {
            }
        }
        return countDownTimer
    }

    fun startTimer() {
        viewModel.setTimeLeftInMillis(workPeriodInMillis)
        createTimer().start()

        isTimerInitialized = true
        timerRunning = true
    }

    fun resumeTimer() {
        createTimer().start()

        timerRunning = true
    }

    fun pauseTimer() {
        countDownTimer.cancel()

        timerRunning = false
    }

    fun stopTimer() {
        countDownTimer.cancel()

        isTimerInitialized = false
        timerRunning = false
    }

    companion object {
        val workPeriodInMillis: Long = 1500000L
        var timerRunning = false
        var isTimerInitialized = false

    }
}