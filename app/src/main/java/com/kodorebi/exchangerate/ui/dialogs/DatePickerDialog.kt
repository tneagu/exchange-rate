package com.kodorebi.exchangerate.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import kotlinx.android.synthetic.main.dialog_date_picker.*
import com.kodorebi.exchangerate.R
import com.kodorebi.exchangerate.core.extensions.DatePickerComponentsOrder
import com.kodorebi.exchangerate.core.extensions.setComponentsOrder
import com.kodorebi.exchangerate.core.extensions.setTime
import com.kodorebi.exchangerate.core.ui.DialogBase
import com.kodorebi.exchangerate.core.utils.UCalendar
import java.util.*

class DatePickerDialog(context: Context) : DialogBase(context){
    interface Listener {
        fun onAccept(sender : DatePickerDialog, value: Date)
        fun onCancel(sender : DatePickerDialog)
    }

    private var isInternalCall = false
    private val calendar = UCalendar.utcCalendar()

    var listener: Listener? = null
    var title: String = ""
        set(value) {
            field = value
            titleTextView?.text = value
        }

    var date: Date = Date()
        set(value){
            field = value
            if (!isInternalCall) {
                updateViewFromDate()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_date_picker)

        setupDatePickerView()

        titleTextView.text = title
        updateViewFromDate()

        setCanceledOnTouchOutside(false)
        cancelButton.setOnClickListener { delayed { listener?.onCancel(this) } }
        acceptButton.setOnClickListener {
            datePickerView.requestFocus()
            delayed { listener?.onAccept(this, date) }
        }


    }

    private fun setupDatePickerView(){
        datePickerView.setComponentsOrder(DatePickerComponentsOrder.DMY)

        calendar.set(0,0,1)
        datePickerView.minDate = calendar.timeInMillis

        calendar.time = Date()
        calendar.add(Calendar.YEAR, 1000)
        datePickerView.maxDate = calendar.timeInMillis
    }

    private fun updateViewFromDate(){
        calendar.time = date
        calendar.setTime(0,0,0,0)

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        datePickerView?.init(year, month, day, this::updateDateFromView)
    }

    private fun internalCall(block : () -> Unit){
        isInternalCall = true
        block()
        isInternalCall = false
    }

    private fun updateDateFromView(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        calendar.set(
            year,
            month,
            day
        )

        internalCall {
            date = calendar.time
        }
    }
}