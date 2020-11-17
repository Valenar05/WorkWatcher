package com.tknape.workwatcher

import android.os.Bundle
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
}