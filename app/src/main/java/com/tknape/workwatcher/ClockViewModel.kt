package com.tknape.workwatcher

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import com.tknape.workwatcher.clock.Clock
import com.tknape.workwatcher.di.AppComponent
import com.tknape.workwatcher.di.DaggerClockComponent
import javax.inject.Inject

class ClockViewModel(application: WorkWatcherApp) : AndroidViewModel(application), IClockViewModel {

    @Inject
    lateinit var clock : Clock

    private val appComponent: AppComponent = application.appComponent

    private val notification: TimerNotification

    init {
        DaggerClockComponent.builder()
            .appComponent(appComponent)
            .build()
            .inject(this)

        clock.setTimeLeftInMillis(clock.initialSessionDurationInMillis)

        notification = TimerNotification(application.baseContext)

        clock.hasTimerFinished.observeForever {
            if (it!!) {
                clock.resetHasTimerFinishedFlag()
                sendNotification()
            }
        }

    }

    val isTimerRunning: LiveData<Boolean> = clock.isTimerRunning

    val timerProgressInPercents: LiveData<Float> =
        map(clock.timeLeftInMillis) { timeLeft ->
            (100 - (timeLeft.toFloat() / clock.initialSessionDurationInMillis.toFloat() * 100))
        }

    val currentSessionType: LiveData<String> = clock.currentSessionType

    val workSessionsUntilBigBreak: LiveData<String> = clock.workSessionsUntilBigBreak


    val formattedTimeLeftInMillis : LiveData<String> = map(clock.timeLeftInMillis) { time ->
        "${if (time / 60000 < 10) {"0"} else {""}}${time / 60000}:${if((time % 60000) / 1000 < 10) {"0"} else {""}}${(time % 60000) / 1000}" //TODO make string formatting more readable
    }

    fun sendNotification() {
        val timeLeftInSession = formattedTimeLeftInMillis.value!!
        val sessionType = clock.currentSessionType.value!!
        notification.sendNotification(timeLeftInSession, sessionType)
    }

    override fun startPauseClock() {
        clock.startPauseClock()
        sendNotification()
    }

    override fun stopClock() {
        clock.stopClock()
        sendNotification()
    }


    override fun isTimerRunning(): Boolean {
        return clock.isTimerRunning()
    }

    override fun skipToNextSession() {
        clock.skipToNextSession()
        sendNotification()
    }

    companion object {
        var isSettingsScreenDisplayed = false
    }
}