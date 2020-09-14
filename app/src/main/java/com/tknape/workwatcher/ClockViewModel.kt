package com.tknape.workwatcher

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tknape.workwatcher.Clock.Clock

class ClockViewModel : ViewModel(), IClockViewModel {

    private val clock: Clock

    init {
        clock = Clock()
        clock.setTimeLeftInMillis(clock.cycleHandler.getCycleLengthInMillis())
    }

    val isTimerRunning: LiveData<Boolean> = clock.isTimerRunning

    val timerProgressInPercents: LiveData<Float> =
        Transformations.map(clock.timeLeftInMillis) { timeLeft ->
            (100 - (timeLeft.toFloat() / clock.initialSessionDurationInMillis.toFloat() * 100))
        }

    val currentSessionType: LiveData<String> =
        Transformations.map(clock.cycleHandler.currentSessionCycle) { currentSessionCycle ->
            getSessionType(currentSessionCycle)
        }

    val workSessionsUntilBigBreak: LiveData<String> =
        Transformations.map(clock.cycleHandler.currentSessionCycle) { currentSessionCycle ->
            getSessionToBigBreakString(currentSessionCycle)
        }


    val formattedTimeLeftInMillis : LiveData<String> = Transformations.map(clock.timeLeftInMillis) { time ->
        "${if (time / 60000 < 10) {"0"} else {""}}${time / 60000}:${if((time % 60000) / 1000 < 10) {"0"} else {""}}${(time % 60000) / 1000}" //TODO make string formatting more readable
    }

    override fun startPauseClock() {

        if (!clock.isTimerRunning() && clock.hasTimerBeenStarted) {
            clock.resumeTimer()
            Log.d("Timer", "Resuming timer")
        }
        else if (clock.isTimerRunning() && clock.hasTimerBeenStarted) {
            clock.pauseTimer()
            Log.d("Timer", "Stopping timer")
            Log.d("Timer", clock.timeLeftInMillis.value.toString())
        }
        else {
            clock.startTimer()
            Log.d("Timer", "Starting timer")
            Log.d("Timer", clock.timeLeftInMillis.value.toString())
        }
    }

    override fun stopClock() {
        clock.stopTimer()
        clock.updateInitialSessionDuration()
        val nextSessionDuration = clock.cycleHandler.getCycleLengthInMillis()
        clock.setTimeLeftInMillis(nextSessionDuration)
    }

    override fun skipToNextSession() {
        clock.skipToNextSession()
        val nextSessionDuration = clock.cycleHandler.getCycleLengthInMillis()
        clock.setTimeLeftInMillis(nextSessionDuration)
    }



    override fun isTimerRunning(): Boolean {
        return clock.isTimerRunning()
    }

    private fun getSessionType(sessionCycle: Int): String {
        when(sessionCycle) {
            1, 3, 5, 7 -> return "Work Session"
            2, 4, 6 -> return "Break"
            0 -> return "Big Break"
            else -> return "##Error"
        }
    }

    private fun getSessionToBigBreakString(sessionCycle: Int): String {
        when(sessionCycle) {
            0, 1 -> return "4 Work sessions until big break"
            2, 3 -> return "3 Work sessions until big break"
            4, 5 -> return "2 Work sessions until big break"
            6, 7 -> return "Big break coming up next!"
            else -> return "##Error"
        }
    }

}