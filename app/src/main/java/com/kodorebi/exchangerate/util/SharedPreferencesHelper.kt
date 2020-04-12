package com.kodorebi.exchangerate.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.kodorebi.exchangerate.R

/**
 * Created by TNE17909 on 4/11/2020.
 * Copyright © 2019 OpenGroupe. All rights reserved.
 */
class SharedPreferencesHelper {

    companion object{
        private var prefs: SharedPreferences? = null
        private var context: Context? = null

        @Volatile private var instance: SharedPreferencesHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper = instance ?: synchronized(LOCK){
            instance ?: buildHelper(context).also{
                instance = it
            }
        }

        private fun buildHelper(context: Context): SharedPreferencesHelper{
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            this.context = context
            return SharedPreferencesHelper()
        }
    }



    fun getRefreshTime() : Long{
        val key = context?.getString(R.string.pref_key_refresh_interval)
        val s = prefs?.getString(key, null)
        return s?.toLong() ?: 3
    }

    fun putAvailableCurrency(currencies : List<String>){
        val sb = StringBuilder()
        for (i in currencies.indices) {
            sb.append(currencies[i]).append(",")
        }
        prefs?.edit(commit = true){
            putString(context?.getString(R.string.pref_key_available_currency), sb.toString())
        }
    }

    fun getAvailableCurrency() : List<String>?{
        val key = context?.getString(R.string.pref_key_available_currency)
        val s = prefs?.getString(key, null)
        var result : List<String>? = null
        s?.let {
            result = it.split(",").filter { !it.isEmpty() }.toList()
        }
        return result
    }

    fun getSavedCurrency() : String{
        val key = context?.getString(R.string.pref_key_set_currency)
        val s = prefs?.getString(key, null)
        return s ?: "EUR"
    }
}