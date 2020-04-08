package com.kodorebi.kotlinstarter.core.extensions

import com.kodorebi.kotlinstarter.core.utils.UCalendar
import java.util.*

fun Calendar.setTime(h: Int, m: Int, s: Int, ms: Int) {
    UCalendar.set(this, h, m, s, ms)
}