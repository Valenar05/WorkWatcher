package com.tknape.workwatcher

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import com.tknape.workwatcher.Clock.Clock
import com.tknape.workwatcher.di.AppComponent
import com.tknape.workwatcher.di.DaggerAppComponent
import javax.inject.Inject

class ClockViewModel(application: WorkWatcherApp) : AndroidViewModel(application), IClockViewModel {

    @Inject
    lateinit var clock : Clock

//    @Inject
//    lateinit var application: WorkWatcherApp


    init {

        DaggerAppComponent.builder()
            .build()

        clock.setTimeLeftInMillis(clock.cycleHandler.getCycleLengthInMillis())
    }

    val isTimerRunning: LiveData<Boolean> = clock.isTimerRunning

    val timerProgressInPercents: LiveData<Float> =
        map(clock.timeLeftInMillis) { timeLeft ->
            (100 - (timeLeft.toFloat() / clock.initialSessionDurationInMillis.toFloat() * 100))
        }

    val currentSessionType: LiveData<String> =
        map(clock.cycleHandler.currentSessionCycle) { currentSessionCycle ->
            getSessionType(currentSessionCycle)
        }

    val workSessionsUntilBigBreak: LiveData<String> =
        map(clock.cycleHandler.currentSessionCycle) { currentSessionCycle ->
            getSessionsLeftToBigBreakString(currentSessionCycle)
        }

    val formattedTimeLeftInMillis : LiveData<String> = map(clock.timeLeftInMillis) { time ->
        "${if (time / 60000 < 10) {"0"} else {""}}${time / 60000}:${if((time % 60000) / 1000 < 10) {"0"} else {""}}${(time % 60000) / 1000}" //TODO make string formatting more readable
    }

    override fun startPauseClock() {
        clock.startPauseClock()
    }

    override fun stopClock() {
        clock.stopClock()
    }


    override fun isTimerRunning(): Boolean {
        return clock.isTimerRunning()
    }

    override fun skipToNextSession() {
        clock.skipToNextSession()
    }

    private fun getSessionType(sessionCycle: Int): String {
        when(sessionCycle) {
            1, 3, 5, 7 -> return "Work Session"
            2, 4, 6 -> return "Break"
            0 -> return "Big Break"
            else -> return "##Error"
        }
    }

    private fun getSessionsLeftToBigBreakString(sessionCycle: Int): String {
        when(sessionCycle) {
            0, 1 -> return "4 Work sessions until big break"
            2, 3 -> return "3 Work sessions until big break"
            4, 5 -> return "2 Work sessions until big break"
            6, 7 -> return "Big break coming up next!"
            else -> return "##Error"
        }
    }
}