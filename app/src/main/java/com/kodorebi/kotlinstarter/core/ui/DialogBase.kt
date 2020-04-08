package com.kodorebi.kotlinstarter.core.ui

import android.app.Dialog
import android.content.Context
import android.os.Handler
import com.kodorebi.kotlinstarter.R

abstract class DialogBase(context: Context) : Dialog(context, R.style.AppTheme_Dialog){
    companion object{
        private val handler = Handler()
        private const val actionDelay : Long = 100
    }


    protected fun delayed(delay: Long, block: () -> Unit){
        handler.postDelayed(block, delay)
    }

    protected fun delayed( block: () -> Unit) {
        handler.postDelayed(block, actionDelay)
    }
}