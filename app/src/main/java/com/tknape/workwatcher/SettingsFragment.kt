package com.tknape.workwatcher

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onStart() {
        super.onStart()
        ClockViewModel.isSettingsScreenDisplayed = true
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onStop() {
        super.onStop()
        ClockViewModel.isSettingsScreenDisplayed = false
    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        if (preference is TimePreference) {
            val timePickerDialog = TimePreferenceDialog.newInstance(preference.key)

            timePickerDialog.setTargetFragment(this, 0)
            timePickerDialog.show(parentFragmentManager, DIALOG_FRAGMENT_TAG)
        }
        else {
            super.onDisplayPreferenceDialog(preference)
        }
    }

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "TimePickerDialog"
    }
}