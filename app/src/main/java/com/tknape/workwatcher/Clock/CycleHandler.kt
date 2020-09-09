package com.tknape.workwatcher.Clock

import android.util.Log

class CycleHandler {

    private var currentSessionNumber = 1

    private fun getSessionCycle(): Int {
        return currentSessionNumber % 8
    }

    fun resetSessionNumber() {
        currentSessionNumber = 1
    }

    fun switchToNextSession() {
        currentSessionNumber++
    }

    fun getCycleLengthInMillis(): Long {
        val currentCycle = getSessionCycle()
        when (currentCycle) {
            1 -> return 1500000L //  15000L
            2 -> return 300000L //  5000L
            3 -> return 1500000L // 15000L
            4 -> return 300000L //  5000L
            5 -> return 1500000L //  15000L
            6 -> return 300000L //  5000L
            7 -> return 1500000L // 15000L
            0 -> return 900000L // 10000L
            else -> {
                Log.d("CycleHandler", "Invalid cycle number. Resetting cycles")
                return 1
            }
        }
    }
}
