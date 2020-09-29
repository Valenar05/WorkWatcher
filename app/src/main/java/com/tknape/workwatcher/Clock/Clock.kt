package com.tknape.workwatcher.Clock

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


object Clock {

    lateinit var countDownTimer: CountDownTimer
    val cycleHandler = CycleHandler()
    var hasTimerBeenStarted = false

    private val mutableTimeLeftInMillis: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }
    private val mutableIsTimerRunning: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val timeLeftInMillis: LiveData<Long> = mutableTimeLeftInMillis
    val isTimerRunning: LiveData<Boolean> = mutableIsTimerRunning // TODO merge into one display state liveData
    var initialSessionDurationInMillis: Long = cycleHandler.getCycleLengthInMillis()

    init {
        createTimer()
    }

    fun setTimeLeftInMillis(time: Long) {
        mutableTimeLeftInMillis.value = time
    }

    fun startTimer() {
        updateInitialSessionDuration()
        setTimeLeftInMillis(initialSessionDurationInMillis)
        createTimer().start()

        hasTimerBeenStarted = true
        switchTimerRunning(true)
    }

    fun startPauseClock() {

        if (!isTimerRunning() && hasTimerBeenStarted) {
            resumeTimer()
            Log.d("Timer", "Resuming timer")
        }
        else if (isTimerRunning() && hasTimerBeenStarted) {
            pauseTimer()
            Log.d("Timer", "Stopping timer")
            Log.d("Timer", timeLeftInMillis.value.toString())
        }
        else {
            startTimer()
            Log.d("Timer", "Starting timer")
            Log.d("Timer", timeLeftInMillis.value.toString())
        }
    }

    fun stopClock() {
        stopTimer()
        updateInitialSessionDuration()
        val nextSessionDuration = cycleHandler.getCycleLengthInMillis()
        setTimeLeftInMillis(nextSessionDuration)
    }

    fun skipToNextSession() {
        setTimerToNextSession()
        val nextSessionDuration = cycleHandler.getCycleLengthInMillis()
        setTimeLeftInMillis(nextSessionDuration)
    }


    fun resumeTimer() {
        createTimer().start()

        switchTimerRunning(true)
    }

    fun pauseTimer() {
        countDownTimer.cancel()

        switchTimerRunning(false)
    }

    fun setTimerToNextSession() {
        countDownTimer.cancel()
        cycleHandler.switchToNextSession()
        switchTimerRunning(false)
        hasTimerBeenStarted = false
        updateInitialSessionDuration()
    }

    fun stopTimer() {
        countDownTimer.cancel()
        cycleHandler.resetSessionNumber()

        hasTimerBeenStarted = false
        switchTimerRunning(false)
    }

    fun updateInitialSessionDuration() {
        initialSessionDurationInMillis = cycleHandler.getCycleLengthInMillis()
    }

    fun isTimerRunning(): Boolean {
        return isTimerRunning.value ?: false
    }

    fun switchTimerRunning(boolean: Boolean) {
        mutableIsTimerRunning.value = boolean
    }

    fun createTimer(): CountDownTimer {
        countDownTimer = object : CountDownTimer(timeLeftInMillis.value ?:cycleHandler.getCycleLengthInMillis(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                setTimeLeftInMillis(millisUntilFinished)
            }

            override fun onFinish() {
                cycleHandler.switchToNextSession()
                switchTimerRunning(false)
                hasTimerBeenStarted = false
                updateInitialSessionDuration()
                setTimeLeftInMillis(initialSessionDurationInMillis)
            }
        }
        return countDownTimer
    }
}