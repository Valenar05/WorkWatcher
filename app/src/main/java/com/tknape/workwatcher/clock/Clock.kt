package com.tknape.workwatcher.clock

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.tknape.workwatcher.AlarmHandler
import com.tknape.workwatcher.VibrationHandler
import com.tknape.workwatcher.WorkWatcherApp
import com.tknape.workwatcher.di.DaggerClockComponent

class Clock(
    val application: WorkWatcherApp,
    val cycleHandler: CycleHandler,
    val alarmHandler: AlarmHandler,
    val vibrationHandler: VibrationHandler
) {

    lateinit var countDownTimer: CountDownTimer

    var hasTimerBeenStarted = false

    var initialSessionDurationInMillis: Long

    private val mutableTimeLeftInMillis: MutableLiveData<Long> by lazy {
        MutableLiveData<Long>()
    }
    private val mutableHasTimerFinished: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    private val mutableIsTimerRunning: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val hasTimerFinished: LiveData<Boolean> = mutableHasTimerFinished
    val timeLeftInMillis: LiveData<Long> = mutableTimeLeftInMillis
    val isTimerRunning: LiveData<Boolean> = mutableIsTimerRunning

    val currentSessionType: LiveData<String>

    val workSessionsUntilBigBreak: LiveData<String>

    init {
        DaggerClockComponent.builder().appComponent(application.appComponent).build()

        initialSessionDurationInMillis = cycleHandler.getCycleLengthInMillis()
        currentSessionType = cycleHandler.currentSessionType
        workSessionsUntilBigBreak = cycleHandler.workSessionsUntilBigBreak
        createTimer()

    }


    fun setTimeLeftInMillis(time: Long) {
        mutableTimeLeftInMillis.value = time
    }

    private fun startTimer() {
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
        } else if (isTimerRunning() && hasTimerBeenStarted) {
            pauseTimer()
            Log.d("Timer", "Stopping timer")
            Log.d("Timer", timeLeftInMillis.value.toString())
        } else {
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


    private fun resumeTimer() {
        createTimer().start()

        switchTimerRunning(true)
    }

    private fun pauseTimer() {
        countDownTimer.cancel()

        switchTimerRunning(false)
    }

    private fun setTimerToNextSession() {
        countDownTimer.cancel()
        cycleHandler.switchToNextSession()
        switchTimerRunning(false)
        hasTimerBeenStarted = false
        updateInitialSessionDuration()
    }

    private fun stopTimer() {
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

    fun resetHasTimerFinishedFlag() {
        mutableHasTimerFinished.value = false
    }

    private fun createTimer(): CountDownTimer {
        countDownTimer = object :
            CountDownTimer(timeLeftInMillis.value ?: cycleHandler.getCycleLengthInMillis(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                setTimeLeftInMillis(millisUntilFinished)
            }

            override fun onFinish() {
                cycleHandler.switchToNextSession()

                if (isSoundNotificationEnabled()) {
                    alarmHandler.playRingtone()
                }

                if (isContinuousSessionsEnabled()) {
                    startTimer()
                } else {

                    switchTimerRunning(false)
                    hasTimerBeenStarted = false
                    updateInitialSessionDuration()
                    setTimeLeftInMillis(initialSessionDurationInMillis)
                    mutableHasTimerFinished.value = true
                }

                if (isVibrationNotificationEnabled()) {
                    vibrationHandler.vibrate()
                }

            }
        }
        return countDownTimer
    }

    private fun isContinuousSessionsEnabled(): Boolean {
        return PreferenceManager
            .getDefaultSharedPreferences(application)
            .getBoolean("start_next_session_automatically", false)
    }

    private fun isSoundNotificationEnabled(): Boolean {
        return PreferenceManager
            .getDefaultSharedPreferences(application)
            .getBoolean("enable_sound_notification", false)
    }

    private fun isVibrationNotificationEnabled(): Boolean {
        return PreferenceManager
            .getDefaultSharedPreferences(application)
            .getBoolean("enable_vibration_notification", false)
    }

}