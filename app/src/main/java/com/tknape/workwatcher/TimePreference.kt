package com.tknape.workwatcher

import android.content.Context
import android.util.AttributeSet
import androidx.preference.DialogPreference
import androidx.preference.PreferenceManager
import com.tknape.workwatcher.Commons.Companion.convertMinutesAfterMidnightToHourlyTime

class TimePreference(context: Context?, attributes: AttributeSet?): DialogPreference(context, attributes) {

    fun getSavedMinutesAfterMidnight(): Int {
        return super.getPersistedInt(DEFAULT_MINUTES_FROM_MIDNIGHT) // TODO fetch default value from preferences.xml
    }

    fun saveMinutesAfterMidnight(minutes: Int) {
        super.persistInt(minutes)
        notifyChanged()
    }

    override fun onSetInitialValue(defaultValue: Any?) {
        super.onSetInitialValue(defaultValue)
        summary = convertMinutesAfterMidnightToHourlyTime(getSavedMinutesAfterMidnight())
    }

    companion object {
        private const val DEFAULT_HOUR = 8
        private const val DEFAULT_MINUTES_FROM_MIDNIGHT = DEFAULT_HOUR * 60
    }

}