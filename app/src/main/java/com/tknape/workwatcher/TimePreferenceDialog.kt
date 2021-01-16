package com.tknape.workwatcher

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.preference.PreferenceDialogFragmentCompat
import com.tknape.workwatcher.Commons.Companion.convertMinutesAfterMidnightToHourlyTime

class TimePreferenceDialog: PreferenceDialogFragmentCompat() {

    lateinit var timepicker: TimePicker

    override fun onCreateDialogView(context: Context?): View {
        timepicker = TimePicker(context)
        return timepicker
    }

    override fun onBindDialogView(view: View?) {
        super.onBindDialogView(view)

        val minutesAfterMidnight = (preference as TimePreference)
            .getSavedMinutesAfterMidnight()

        timepicker.setIs24HourView(true)
        timepicker.hour = minutesAfterMidnight / 60
        timepicker.minute = minutesAfterMidnight % 60
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        if(positiveResult) {
            val minutesAfterMidnight = timepicker.hour * 60 + timepicker.minute
            (preference as TimePreference).saveMinutesAfterMidnight(minutesAfterMidnight)
            preference.summary = convertMinutesAfterMidnightToHourlyTime(minutesAfterMidnight)
        }
    }

    companion object {
        fun newInstance(key: String): TimePreferenceDialog {
            val fragment = TimePreferenceDialog()
            val bundle = Bundle(1)
            bundle.putString(ARG_KEY, key)
            fragment.arguments = bundle

            return fragment
        }
    }
}