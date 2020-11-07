package com.tknape.workwatcher

import com.tknape.workwatcher.clock.CycleHandler
import com.tknape.workwatcher.TestUtilities.InstantExecutorExtension
import org.junit.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.IntStream.range
import java.util.stream.Stream

@ExtendWith(InstantExecutorExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CycleHandlerTest {

    @ParameterizedTest(name = "Session no. {0} should return cycle {1}")
    @MethodSource("getSessionCycleTestData")
    fun `getSessionCycle returns correct cycle`(
        sessionSwitches: Int,
        expectedCycle: Int
        ) {

        val cycleHandler = CycleHandler()

        for (i in range(0, sessionSwitches)) {
            cycleHandler.switchToNextSession()
        }

        val testedSession = cycleHandler.getSessionCycle()
        Assert.assertTrue(testedSession == expectedCycle)
    }

    @ParameterizedTest(name = "{index}: Session no. {0} should return length {2}")
    @MethodSource("getSessionCycleTestData")
    fun `getCycleLengthInMillis return correct session length`(
        sessionNumber: Int,
        cycle: Int,
        expectedSessionLength: Long
    ) {

        val cycleHandler = CycleHandler()

        for (i in range(0, sessionNumber)) {
            cycleHandler.switchToNextSession()
        }

        val returnedSessionLength = cycleHandler.getCycleLengthInMillis()
        Assert.assertTrue(expectedSessionLength == returnedSessionLength)
    }

    @ParameterizedTest(name = "{index}: Session no. {0} should reset to cycle 1")
    @MethodSource("getSessionCycleTestData")
    fun `resetSessionNumber sets session number to 1`(
        sessionNumber: Int
    ) {

        val cycleHandler = CycleHandler()

        for (i in range(0, sessionNumber)) {
            cycleHandler.switchToNextSession()
        }

        cycleHandler.resetSessionNumber()

        val actualSessionNumber = cycleHandler.currentSessionNumber.value
        Assert.assertTrue(actualSessionNumber == 1)
    }

    private companion object {
        @JvmStatic
        fun getSessionCycleTestData() = Stream.of(
            Arguments.of(0, 1, 1500000L),
            Arguments.of(1, 2, 300000L),
            Arguments.of(2, 3, 1500000L),
            Arguments.of(3, 4, 300000L),
            Arguments.of(4, 5, 1500000L),
            Arguments.of(5, 6, 300000L),
            Arguments.of(6, 7, 1500000L),
            Arguments.of(7, 0, 900000L),
            Arguments.of(8, 1, 1500000L),
            Arguments.of(9, 2, 300000L),
            Arguments.of(10, 3, 1500000L),
            Arguments.of(11, 4, 300000L),
            Arguments.of(12, 5, 1500000L),
            Arguments.of(13, 6, 300000L),
            Arguments.of(14, 7, 1500000L),
            Arguments.of(15, 0, 900000L)
        )
    }
}
