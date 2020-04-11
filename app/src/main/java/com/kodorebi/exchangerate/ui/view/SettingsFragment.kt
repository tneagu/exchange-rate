package com.kodorebi.exchangerate.ui.view

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.kodorebi.exchangerate.R


/**
 * Created by TNE17909 on 4/11/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val refreshIntervalPreference = findPreference(getString(R.string.pref_key_refresh_interval)) as ListPreference?
        refreshIntervalPreference?.let {
            var s : String = ""
            s = getString(R.string.refresh_interval) + " (" + refreshIntervalPreference.entry + ")"
            it.title = s
            it.setOnPreferenceChangeListener { preference, newValue ->
                if (preference is ListPreference) {
                    val index = preference.findIndexOfValue(newValue.toString())
                    val entry = preference.entries.get(index)
                    s = getString(R.string.refresh_interval) + " (" + entry + ")"
                    preference.title = s
                }

                true
            }
        }

    }

}