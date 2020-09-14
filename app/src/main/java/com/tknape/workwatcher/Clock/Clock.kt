package com.tknape.workwatcher.Clock

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tknape.workwatcher.IClockViewModel

class Clock(val viewModel: IClockViewModel) {

    private val mutableIsTimerRunning: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isTimerRunning: LiveData<Boolean> = mutableIsTimerRunning

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
                switchTimerRunning(false)
                isTimerInitialized = false
                updateTotalSessionDuration()
                viewModel.setTimeLeftInMillis(totalSessionDurationInMillis)
            }
        }
        return countDownTimer
    }

    fun startTimer() {
        updateTotalSessionDuration()
        viewModel.setTimeLeftInMillis(totalSessionDurationInMillis)
        createTimer().start()

        isTimerInitialized = true
        switchTimerRunning(true)
    }

    fun resumeTimer() {
        createTimer().start()

        switchTimerRunning(true)
    }

    fun pauseTimer() {
        countDownTimer.cancel()

        switchTimerRunning(false)
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
        switchTimerRunning(false)
        isTimerInitialized = false
        updateTotalSessionDuration()
    }

    fun stopTimer() {
        countDownTimer.cancel()
        cycleHandler.resetSessionNumber()

        isTimerInitialized = false
        switchTimerRunning(false)
    }

    fun isCountDownTimerInitialized(): Boolean {
        return this::countDownTimer.isInitialized
    }

    fun updateTotalSessionDuration() {
        totalSessionDurationInMillis = cycleHandler.getCycleLengthInMillis()
    }

    fun isTimerRunning(): Boolean {
        return isTimerRunning.value ?: false
    }

    fun switchTimerRunning(boolean: Boolean) {
        mutableIsTimerRunning.value = boolean
    }

    companion object {
        var isTimerInitialized = false

    }
}