package com.tknape.workwatcher

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.rules.TestRule

class ClockViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


//    @Test
//    fun shouldStartCountDownTimer() {
//        val clock = Clock()
//        clock.startTimer()
//        Assert.assertNotNull(clock.countDownTimer)
//    }
}