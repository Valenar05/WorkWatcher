package com.tknape.workwatcher.clock

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tknape.workwatcher.di.ApplicationScope

@ApplicationScope
class Clock {

    lateinit var countDownTimer: CountDownTimer
    private val cycleHandler = CycleHandler()
    var hasTimerBeenStarted = false


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
    var initialSessionDurationInMillis: Long = cycleHandler.getCycleLengthInMillis()

    val currentSessionType: LiveData<String> = cycleHandler.currentSessionType

    val workSessionsUntilBigBreak: LiveData<String> = cycleHandler.workSessionsUntilBigBreak

    init {
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
                mutableHasTimerFinished.value = true
//
//                val v =
//                   activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    v!!.vibrate(
//                        VibrationEffect.createOneShot(
//                            500,
//                            VibrationEffect.DEFAULT_AMPLITUDE
//                        )
//                    )
//                } else {
//                    //deprecated in API 26
//                    v!!.vibrate(500)
//                }

            }
        }
        return countDownTimer
    }
}