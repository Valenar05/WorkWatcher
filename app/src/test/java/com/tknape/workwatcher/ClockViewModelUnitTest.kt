package com.tknape.workwatcher

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ClockViewModelUnitTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Test
    fun shouldStartCountDownTimer() {
        val viewmodel = ClockViewModel()
        Assert.assertNull("${viewmodel.formattedTimeLeftInMillis.value}",viewmodel.formattedTimeLeftInMillis.value)
        viewmodel.startPauseClock()
        Assert.assertNotNull("${viewmodel.formattedTimeLeftInMillis.value}",viewmodel.formattedTimeLeftInMillis.value)

    }
}