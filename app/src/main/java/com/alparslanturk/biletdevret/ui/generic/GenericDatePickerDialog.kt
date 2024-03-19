package com.alparslanturk.biletdevret.ui.generic

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.alparslanturk.biletdevret.R
import com.alparslanturk.biletdevret.data.entities.enums.DateFormatType
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GenericDatePickerDialog(
    private var editText: EditText,
    private var isMaxDateToday: Boolean = true,
    private var isMinDateToday: Boolean = false,
    private var minDate: Date? = null,
    private var isTimeRequired: Boolean = false,
    private var onDateSetListener: DatePickerDialog.OnDateSetListener? = null,
) : DialogFragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(requireActivity(), R.style.DatePickerTheme, onDateSetListener ?: this, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])

        if (isMaxDateToday) setMaxDate(dialog, Calendar.getInstance().time)

        if (isMinDateToday) setMinDate(dialog, minDate ?: Calendar.getInstance().time)

        if (isDateAlreadySelected) updateDialogDate(dialog)

        dialog.setCancelable(true)

        dialog.datePicker.firstDayOfWeek = Calendar.MONDAY

        return dialog
    }

    private fun setMinDate(dialog: DatePickerDialog, minDate: Date) {
        dialog.datePicker.minDate = minDate.time - 1000
    }

    private fun setMaxDate(dialog: DatePickerDialog, maxDate: Date) {
        dialog.datePicker.maxDate = maxDate.time
    }

    private val isDateAlreadySelected: Boolean = !TextUtils.isEmpty(editText.text?.toString())

    @SuppressLint("SimpleDateFormat")
    private fun updateDialogDate(dialog: DatePickerDialog) {
        try {
            val date = SimpleDateFormat(DateFormatType.DATE_WITH_DOT.format).parse(editText.text.toString())
            if (date != null) {
                val calendar = Calendar.getInstance()
                calendar.time = date
                val year = calendar[Calendar.YEAR]
                val month = calendar[Calendar.MONTH]
                val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

                dialog.updateDate(year, month, dayOfMonth)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        if (isTimeRequired) {
            val timePickerDialog = TimePickerDialog(requireActivity(), this, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], true)

            timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel)) { _, which ->
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    editText.setText("")
                }
            }

            timePickerDialog.show()
        }

        populateSetDate(dayOfMonth, month + 1, year)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) = populateSetTime(hourOfDay, minute)

    @SuppressLint("SetTextI18n")
    private fun populateSetDate(day: Int, month: Int, year: Int) {
        val dayStr = if (day < 10) "0$day" else day.toString()
        val monthStr = if (month < 10) "0$month" else month.toString()
        val yearStr = year.toString()

        editText.setText("$dayStr.$monthStr.$yearStr")
    }

    @SuppressLint("SetTextI18n")
    private fun populateSetTime(hour: Int, minute: Int) {
        val hourStr = if (hour < 10) "0$hour" else hour.toString()
        val minuteStr = if (minute < 10) "0$minute" else minute.toString()

        editText.setText("${editText.text} $hourStr:$minuteStr")
    }
}