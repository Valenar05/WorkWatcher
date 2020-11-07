package com.tknape.workwatcher.clock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map

class CycleHandler {

    private val mutableCurrentSessionNumber: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    private val currentSessionCycle: LiveData<Int> =
        map(mutableCurrentSessionNumber) { sessionNumber ->
            sessionNumber % 8
        }

    val currentSessionNumber: LiveData<Int> = mutableCurrentSessionNumber

    val currentSessionType: LiveData<String> =
        map(currentSessionCycle) { currentSessionCycle ->
            getSessionType(currentSessionCycle)
        }

    val workSessionsUntilBigBreak: LiveData<String> =
        map(currentSessionCycle) { currentSessionCycle ->
            getSessionsLeftToBigBreakString(currentSessionCycle)
        }

    init {
        mutableCurrentSessionNumber.value = 1
    }

    fun getSessionCycle(): Int {
        val x = currentSessionNumber.value!!
        val currentSessionCycle = x % 8
        return currentSessionCycle
    }

    fun resetSessionNumber() {
        mutableCurrentSessionNumber.value = 1
    }

    fun switchToNextSession() {
        val oldSessionNumber = currentSessionNumber.value
        val newSessionNumber = oldSessionNumber!!.plus(1)
        mutableCurrentSessionNumber.value = newSessionNumber
    }

    fun getCycleLengthInMillis(): Long {
        val currentCycle = getSessionCycle()
        when (currentCycle) {
            1 -> return 1500000L // 15000L
            2 -> return 300000L // 5000L
            3 -> return 1500000L // 15000L
            4 -> return 300000L // 5000L
            5 -> return 1500000L // 15000L
            6 -> return 300000L // 5000L
            7 -> return 1500000L // 15000L
            0 -> return 900000L // 10000L
            else -> {
                Log.d("CycleHandler", "Invalid cycle number. Resetting cycles")
                resetSessionNumber()
                return 1
            }
        }
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
