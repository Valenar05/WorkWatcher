package com.tknape.workwatcher.Clock

import android.os.CountDownTimer
import com.tknape.workwatcher.IClockViewModel

class Clock(val viewModel: IClockViewModel) {


    val cycleHandler = CycleHandler()
    var totalSessionDurationInMillis: Long = cycleHandler.getCycleLengthInMillis()

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
                updateTotalSessionDuration()
            }
        }
        return countDownTimer
    }

    fun startTimer() {
        updateTotalSessionDuration()
        viewModel.setTimeLeftInMillis(totalSessionDurationInMillis)
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
        try {
            countDownTimer.cancel()
        }
        catch (e: UninitializedPropertyAccessException) {
            startTimer()
            countDownTimer.cancel()
        }
        cycleHandler.switchToNextSession()
        timerRunning = false
        isTimerInitialized = false
        updateTotalSessionDuration()
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

    fun updateTotalSessionDuration() {
        totalSessionDurationInMillis = cycleHandler.getCycleLengthInMillis()
    }

    companion object {
        var timerRunning = false
        var isTimerInitialized = false

    }
}