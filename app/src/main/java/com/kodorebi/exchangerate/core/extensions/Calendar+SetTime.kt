package com.kodorebi.exchangerate.core.extensions

import com.kodorebi.exchangerate.core.utils.UCalendar
import java.util.*

fun Calendar.setTime(h: Int, m: Int, s: Int, ms: Int) {
    UCalendar.set(this, h, m, s, ms)
}