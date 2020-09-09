package com.tknape.workwatcher

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MockClockViewModel: IClockViewModel {

    private val mutableTimeLeftInMillis = MutableLiveData<Long>()

    init {
        mutableTimeLeftInMillis.value = 50000L
    }

    override val timeLeftInMillis: LiveData<Long>
        get() = mutableTimeLeftInMillis

    override fun startPauseClock() {
        TODO("Not yet implemented")
    }

    override fun stopClock() {
        TODO("Not yet implemented")
    }

    override fun setTimeLeftInMillis(time: Long) {

    }

    override fun skipToNextSession() {
        TODO("Not yet implemented")
    }

    override fun isTimerRunning(): Boolean {
        TODO("Not yet implemented")
    }
}