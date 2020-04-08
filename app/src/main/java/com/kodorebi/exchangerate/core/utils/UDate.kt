package com.kodorebi.exchangerate.core.utils

import java.util.*

object UDate {
    fun addDays(date: Date, days: Int) : Date {
        val calendar = UCalendar.utcCalendar()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, days)
        return calendar.time
    }

    fun addYears(date: Date, years: Int) : Date {
        val calendar = UCalendar.utcCalendar()
        calendar.time = date
        calendar.add(Calendar.YEAR, years)
        return calendar.time
    }

    fun age(dob: Date, at: Date = Date()) : Int {
        val c1 = UCalendar.utcCalendar()
        c1.time = dob

        val c2 = UCalendar.utcCalendar()
        c2.time = at

        val years = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)
        val m1 = c1.get(Calendar.MONTH)
        val m2 = c2.get(Calendar.MONTH)

        return when{
            m1 > m2 || (m1 == m2) and (c1.get(Calendar.DAY_OF_MONTH) > c2.get(Calendar.DAY_OF_MONTH)) -> years - 1
            else -> years
        }
    }
}