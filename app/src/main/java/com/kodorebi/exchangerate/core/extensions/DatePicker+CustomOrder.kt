package com.kodorebi.exchangerate.core.extensions

import android.content.res.Resources
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView

enum class DatePickerComponentsOrder {
    DMY,
    MDY,
    YMD,
    YDM,
    MYD,
    DYM
}

private const val SPINNER_COUNT = 3

fun DatePicker.setComponentsOrder(order : DatePickerComponentsOrder) {
    val idYear: Int = Resources.getSystem().getIdentifier("year", "id", "android")
    val idMonth: Int = Resources.getSystem().getIdentifier("month", "id", "android")
    val idDay: Int = Resources.getSystem().getIdentifier("day", "id", "android")
    val idLayout: Int = Resources.getSystem().getIdentifier("pickers", "id", "android")

    val spinnerYear = findViewById<NumberPicker>(idYear)
    val spinnerMonth = findViewById<NumberPicker>(idMonth)
    val spinnerDay = findViewById<NumberPicker>(idDay)
    val layout = findViewById<LinearLayout>(idLayout)

    val strOrder = order.name

    layout.removeAllViews()
    for (i in 0 until SPINNER_COUNT) {
        when (strOrder[i]) {
            'Y' -> {
                layout.addView(spinnerYear)
                setImeOptions(spinnerYear, i)
            }
            'M' -> {
                layout.addView(spinnerMonth)
                setImeOptions(spinnerMonth, i)
            }
            'D' -> {
                layout.addView(spinnerDay)
                setImeOptions(spinnerDay, i)
            }
            else -> throw IllegalArgumentException("Invalid")
        }
    }
}

private fun setImeOptions(spinner: NumberPicker, spinnerIndex: Int) {
    val imeOptions: Int = if (spinnerIndex < SPINNER_COUNT - 1) {
        EditorInfo.IME_ACTION_NEXT
    } else {
        EditorInfo.IME_ACTION_DONE
    }
    val idPickerInput = Resources.getSystem()
        .getIdentifier("numberpicker_input", "id", "android")
    val input = spinner.findViewById<View>(idPickerInput) as TextView
    input.imeOptions = imeOptions
}