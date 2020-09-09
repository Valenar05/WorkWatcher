package com.tknape.workwatcher.Clock

import android.os.CountDownTimer
import com.tknape.workwatcher.IClockViewModel

class Clock(val viewModel: IClockViewModel) {


    val cycleHandler = CycleHandler()
    var sessionDurationInMillis: Long = cycleHandler.getCycleLengthInMillis()

    lateinit var countDownTimer: CountDownTimer

    private fun createTimer(): CountDownTimer {
        countDownTimer = object : CountDownTimer(viewModel.timeLeftInMillis.value!!, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                viewModel.setTimeLeftInMillis(millisUntilFinished)
            }

            override fun onFinish() {
                cycleHandler.switchToNextSession()
                timerRunning = false
                isTimerInitialized = false
                updateSessionDuration()
            }
        }
        return countDownTimer
    }

    fun startTimer() {
        updateSessionDuration()
        viewModel.setTimeLeftInMillis(sessionDurationInMillis)
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

    fun skipToNextSession() {
        countDownTimer.cancel()
        cycleHandler.switchToNextSession()
        timerRunning = false
        isTimerInitialized = false
        updateSessionDuration()
    }

    fun stopTimer() {
        countDownTimer.cancel()
        cycleHandler.resetSessionNumber()

        isTimerInitialized = false
        timerRunning = false
    }

    fun isCountDownTimerInitialized(): Boolean {
        return this::countDownTimer.isInitialized
    }

    private fun updateSessionDuration() {
        sessionDurationInMillis = cycleHandler.getCycleLengthInMillis()
    }

    companion object {
        var timerRunning = false
        var isTimerInitialized = false

    }
}