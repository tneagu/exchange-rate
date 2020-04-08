package com.kodorebi.exchangerate.core.utils

import java.util.*

object UCalendar {
    fun utcCalendar() : Calendar = Calendar.getInstance(UTimeZone.utcTimeZone)
    fun set(calendar: Calendar, h: Int, m: Int, s: Int, ms: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, h)
        calendar.set(Calendar.MINUTE, m)
        calendar.set(Calendar.SECOND, s)
        calendar.set(Calendar.MILLISECOND, ms)
    }

    private val sharedUtcCalendar : Calendar = utcCalendar()

    @Synchronized
    fun withSharedUtcCalendar(block: (Calendar) -> Unit){
        block(sharedUtcCalendar)
    }
}