package com.tknape.workwatcher.Clock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class CycleHandler {

    private val mutableCurrentSessionNumber: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val currentSessionNumber: LiveData<Int> = mutableCurrentSessionNumber

    val currentSessionCycle: LiveData<Int> =
        Transformations.map(mutableCurrentSessionNumber) { sessionNumber ->
            sessionNumber % 8
        }

    init {
        mutableCurrentSessionNumber.value = 1
    }

    fun getSessionCycle(): Int {
        val x = currentSessionNumber.value
        val currentSessionCycle = x!! % 8
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
            1 -> return 15000L // 1500000L
            2 -> return 5000L // 300000L
            3 -> return 15000L // 1500000L
            4 -> return 5000L // 300000L
            5 -> return 15000L // 1500000L
            6 -> return 5000L // 300000L
            7 -> return 15000L // 1500000L
            0 -> return 10000L // 900000L
            else -> {
                Log.d("CycleHandler", "Invalid cycle number. Resetting cycles")
                return 1
            }
        }
    }
}
