package com.tknape.workwatcher

import android.os.CountDownTimer
import com.tknape.workwatcher.Clock.Clock
import com.tknape.workwatcher.TestUtilities.InstantExecutorExtension
import org.junit.Assert.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.*
import java.util.stream.Stream


@ExtendWith(InstantExecutorExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClockTest {

    @Test
    fun `Timer is created upon instance initialization`() {
        val clock = Clock()

        assertNotNull(clock.countDownTimer)
    }

    @ParameterizedTest(name = "{index}: timeLeftInMillis should equal {0}")
    @MethodSource("setTimeLeftData")
    fun `setTimeLeftInMillis changes LiveData correctly`(
        timeLeft: Long
    ) {
        val clock = Clock()

        clock.setTimeLeftInMillis(timeLeft)

        assertEquals(clock.timeLeftInMillis.value!!, timeLeft)
    }

//    @Test
//    fun `startTimer switches timerStarted flag to true`() {
//        val clock = spy(Clock::class.java)
//        val mockCountDownTimer = mock(CountDownTimer::class.java)
//
//        `when`(clock.createTimer()).thenReturn(mockCountDownTimer)
//
//        assertFalse(clock.hasTimerBeenStarted)
//        clock.startTimer()
//        assertTrue(clock.hasTimerBeenStarted)
//        clock.startTimer()
//        assertTrue(clock.hasTimerBeenStarted)
//    }

    @Test
    fun `updateInitialSessionDuration updates properly`() {
        // TODO
    }



    private companion object {

        @JvmStatic
        fun setTimeLeftData() = Stream.of(
            Arguments.of(0L),
            Arguments.of(150320L),
            Arguments.of(43920L),
            Arguments.of(4234234230L),
            Arguments.of(5646300L),
            Arguments.of(4363460L),
            Arguments.of(321090L),
            Arguments.of(9000L)
        )
    }
}