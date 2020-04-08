package com.kodorebi.kotlinstarter.core.extensions

import android.view.Gravity
import android.widget.TextView
import android.widget.Toast


fun Toast.showCentered() {
    val v = view.findViewById<TextView>(android.R.id.message)
    v?.gravity = Gravity.CENTER
    this.show()
}