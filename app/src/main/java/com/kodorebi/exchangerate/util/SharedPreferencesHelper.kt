package com.kodorebi.exchangerate.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.core.content.edit
import com.kodorebi.exchangerate.R
import com.kodorebi.exchangerate.app.App

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
        val s = prefs?.getString(key, "")
        return s?.toLong() ?: 3
    }
}