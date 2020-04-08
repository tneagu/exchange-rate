package com.kodorebi.exchangerate.core.extensions

import android.content.Context
import org.threeten.bp.Period
import com.kodorebi.exchangerate.R

fun Period.inWords(context: Context) : String {
    val parts = mutableListOf<String>()
    if (years > 0) {
        val id = if (years == 1) R.string.period_year else R.string.period_years
        val s = context.getString(id)
        parts.add("$years $s")
    }

    if (months > 0) {
        val id = if (months == 1) R.string.period_month else R.string.period_months
        val s = context.getString(id)
        parts.add("$months $s")
    }

    if (days > 0) {
        val id = if (days == 1) R.string.period_day else R.string.period_days
        val s = context.getString(id)
        parts.add("$days $s")
    }

    val strAnd = context.getString(R.string.string_and)

    return when (parts.size) {
        3 -> "${parts[0]}, ${parts[1]} $strAnd ${parts[2]}"
        2 -> "${parts[0]} $strAnd ${parts[1]}"
        1 -> parts[0]
        else -> ""
    }
}