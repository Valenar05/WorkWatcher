package com.tknape.workwatcher.clock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.preference.PreferenceManager
import com.tknape.workwatcher.WorkWatcherApp
import javax.inject.Inject

class CycleHandler @Inject constructor(val application: WorkWatcherApp) {


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
        val sessionLength: Long


        if (isCustomSessionLengthEnabled()) {
            Log.d(TAG, "Custom session length enabled. Fetching session length from preferences...")
            sessionLength =  getCustomSessionLength(currentCycle)
        }
        else {
            Log.d(TAG, "Fetching standard session length...")
            sessionLength = getStandardSessionLength(currentCycle)
        }

        Log.d(TAG, " Session length is: $sessionLength")
        return sessionLength
    }

    private fun getStandardSessionLength(currentCycle: Int): Long {
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
                Log.d(TAG, "Invalid cycle number. Resetting cycles")
                resetSessionNumber()
                return 1500000L
            }
        }
    }

    private fun getCustomSessionLength(currentCycle: Int) : Long {
        when(currentCycle) {
            1,3,5,7 -> return getPreferenceSetting("work_session_length_picker").toLong()
            2,4,6 -> return getPreferenceSetting("small_break_session_length_picker").toLong()
            0 -> return getPreferenceSetting("big_break_session_length_picker").toLong()
            else -> {
                Log.d(TAG, "Invalid cycle number. Resetting cycles")
                resetSessionNumber()
                return 1500000L
            }
        }
    }

    private fun isCustomSessionLengthEnabled(): Boolean {
        return PreferenceManager
            .getDefaultSharedPreferences(application)
            .getBoolean("enabledCustomCycleSettings", false)
    }

    private fun getPreferenceSetting(key: String): String {
        return PreferenceManager
            .getDefaultSharedPreferences(application)
            .getString(key, "error")!!

    }

    private fun getSessionType(sessionCycle: Int): String {
        when (sessionCycle) {
            1, 3, 5, 7 -> return "Work Session"
            2, 4, 6 -> return "Break"
            0 -> return "Big Break"
            else -> return "##Error"
        }
    }

    private fun getSessionsLeftToBigBreakString(sessionCycle: Int): String {
        when (sessionCycle) {
            0, 1 -> return "4 Work sessions until big break"
            2, 3 -> return "3 Work sessions until big break"
            4, 5 -> return "2 Work sessions until big break"
            6, 7 -> return "Big break coming up next!"
            else -> return "##Error"
        }
    }

    companion object {
        private val TAG = CycleHandler::class.java.simpleName

    }
}
