package com.kodorebi.exchangerate.ui.view

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.kodorebi.exchangerate.R
import com.kodorebi.exchangerate.util.SharedPreferencesHelper


/**
 * Created by TNE17909 on 4/11/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
class SettingsFragment : PreferenceFragmentCompat() {
    lateinit var prefHelper : SharedPreferencesHelper

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        prefHelper = SharedPreferencesHelper(requireContext())
        setupRefreshPreference()
        setupBaseCurrencyPreference()
    }

    private fun setupBaseCurrencyPreference() {
        val baseCurrencyPref = findPreference(getString(R.string.pref_key_set_currency)) as ListPreference?
        baseCurrencyPref?.let {

            val currency = prefHelper.getSavedCurrency()
            var title = getString(R.string.base_currency) + " (" + currency + ")"
            it.title = title

            val entries = prefHelper.getAvailableCurrency()
            it.entries = entries?.toTypedArray()
            it.entryValues = entries?.toTypedArray()

            it.setOnPreferenceChangeListener { preference, newValue ->
                if (preference is ListPreference) {
                    val index = preference.findIndexOfValue(newValue.toString())
                    val entry = preference.entries.get(index)
                    title = getString(R.string.base_currency) + " (" + entry + ")"
                    preference.title = title
                }

                true
            }
        }
    }

    private fun setupRefreshPreference() {
        val refreshIntervalPref = findPreference(getString(R.string.pref_key_refresh_interval)) as ListPreference?
        refreshIntervalPref?.let {
            var title : String = ""
            title = getString(R.string.refresh_interval) + " (" + refreshIntervalPref.entry + ")"
            it.title = title
            it.setOnPreferenceChangeListener { preference, newValue ->
                if (preference is ListPreference) {
                    val index = preference.findIndexOfValue(newValue.toString())
                    val entry = preference.entries.get(index)
                    title = getString(R.string.refresh_interval) + " (" + entry + ")"
                    preference.title = title
                }

                true
            }
        }
    }

}