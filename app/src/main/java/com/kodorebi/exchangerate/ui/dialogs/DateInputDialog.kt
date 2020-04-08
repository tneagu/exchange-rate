package com.kodorebi.exchangerate.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.dialog_date_input.*
import kotlinx.android.synthetic.main.dialog_date_picker.acceptButton
import kotlinx.android.synthetic.main.dialog_date_picker.cancelButton
import kotlinx.android.synthetic.main.dialog_date_picker.titleTextView
import com.kodorebi.exchangerate.R
import com.kodorebi.exchangerate.core.extensions.DatePickerComponentsOrder
import com.kodorebi.exchangerate.core.extensions.setTime
import com.kodorebi.exchangerate.core.ui.DialogBase
import com.kodorebi.exchangerate.core.utils.UCalendar
import com.kodorebi.exchangerate.core.utils.UTimeZone
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateInputDialog(context: Context) : DialogBase(context) {
    interface Listener {
        fun onAccept(sender : DateInputDialog, value: Date)
        fun onCancel(sender : DateInputDialog)
    }

    companion object {
        private val datePatternParts : Map<Char, String> by lazy {
            val map = mutableMapOf<Char, String>()
            map['Y'] = "yyyy"
            map['M'] = "MM"
            map['D'] = "dd"

            map
        }

        private val maskPatternParts : Map<Char, String> by lazy {
            val map = mutableMapOf<Char, String>()
            map['Y'] = "[0000]"
            map['M'] = "[00]"
            map['D'] = "[00]"
            map
        }
    }

    private var datePattern = "dd-MM-yyyy"
    private var maskPattern = "[00]-[00]-[0000]"

    private val calendar = UCalendar.utcCalendar()
    private lateinit var maskedTextChangedListener : MaskedTextChangedListener
    private var dateFormat : DateFormat = SimpleDateFormat(datePattern, Locale.getDefault())

    var listener: DateInputDialog.Listener? = null
    var title: String = ""
        set(value) {
            field = value
            titleTextView?.text = value
        }

    var date: Date? = null
        set(value){
            field = value
            updateViewFromDate()
        }

    var componentsOrder: DatePickerComponentsOrder = DatePickerComponentsOrder.DMY
        set(value) {
            updateDatePattern()
            updateMaskPattern()
            updateViewFromDate()
            field = value
        }

    var componentsSeparator = '-'
        set(value) {
            updateDatePattern()
            updateMaskPattern()
            updateViewFromDate()
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_date_input)


        titleTextView.text = title
        updateViewFromDate()

        maskedTextChangedListener = MaskedTextChangedListener.installOn(
            dateEditText,
            maskPattern
        )
        dateEditText.hint = datePattern

        updateDatePattern()
        updateMaskPattern()
        updateViewFromDate()

        setCanceledOnTouchOutside(false)

        cancelButton.setOnClickListener { delayed { listener?.onCancel(this) } }
        acceptButton.setOnClickListener {
            val d = dateFromView()
            if (d == null) {
                Toast.makeText(context, R.string.error_invalid_date, Toast.LENGTH_SHORT).show()
            }
            else {
                delayed { listener?.onAccept(this, d) }
            }
        }

        dateEditText.requestFocus()
        dateEditText.setSelection(0, dateEditText.text.toString().length)
        this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private fun updateDatePattern() {
        val components = componentsOrder.name

        val c1 = datePatternParts[components[0]]
        val c2 = datePatternParts[components[1]]
        val c3 = datePatternParts[components[2]]
        val s = componentsSeparator

        datePattern = "$c1$s$c2$s$c3"
        dateFormat = SimpleDateFormat(datePattern, Locale.getDefault())
        dateFormat.timeZone = UTimeZone.utcTimeZone
    }

    private fun updateMaskPattern() {
        val components = componentsOrder.name

        val c1 = maskPatternParts[components[0]]
        val c2 = maskPatternParts[components[1]]
        val c3 = maskPatternParts[components[2]]
        val s = componentsSeparator
        maskPattern = "$c1$s$c2$s$c3"

        maskedTextChangedListener.primaryFormat = maskPattern
    }

    private fun updateViewFromDate(){
        val d = date ?: run {
            dateEditText?.setText("")
            return
        }

        calendar.time = d
        calendar.setTime(0,0,0,0)

        val stringDate = dateFormat.format(calendar.time)

        dateEditText?.setText(stringDate)
    }

    private fun dateFromView() : Date? {
        var parsedDate : Date? = null

        try {
            parsedDate = dateFormat.parse(dateEditText.text.toString())
        }
        catch (ex: Exception) {
            ex.printStackTrace()
        }

        return parsedDate
    }
}