package com.tknape.workwatcher

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tknape.workwatcher.Clock.Clock
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ClockUnitTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Test
    fun shouldStartCountDownTimer() {
        val clock = Clock(MockClockViewModel())
        Assert.assertFalse(clock.isCountDownTimerInitialized())
        clock.startTimer()
        Assert.assertNotNull(clock.countDownTimer)


    }
}