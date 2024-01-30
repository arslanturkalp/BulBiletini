package com.alparslanturk.kombineapp.ui.generic

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.alparslanturk.kombineapp.data.entities.enums.DateFormatType
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GenericHourMinutePickerDialog(
    private var hourText: EditText,
    private var minuteText: EditText,
    private var onTimeSetListener: TimePickerDialog.OnTimeSetListener? = null
) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val dialog = TimePickerDialog(requireActivity(), onTimeSetListener ?: this, calendar[Calendar.HOUR_OF_DAY], calendar[Calendar.MINUTE], true)

        if (isDateAlreadySelected) updateDialogDate(dialog)

        dialog.setCancelable(true)

        return dialog
    }

    private val isDateAlreadySelected: Boolean = !TextUtils.isEmpty(hourText.text?.toString()) && !TextUtils.isEmpty(minuteText.text?.toString())

    @SuppressLint("SimpleDateFormat")
    private fun updateDialogDate(dialog: TimePickerDialog) {
        try {
            val date = SimpleDateFormat(DateFormatType.TIME_WITH_DOT.format).parse(hourText.text.toString())
            if (date != null) {
                val calendar = Calendar.getInstance()
                calendar.time = date
                val hourOfDay = calendar[Calendar.HOUR_OF_DAY]
                val minute = calendar[Calendar.MINUTE]

                dialog.updateTime(hourOfDay, minute)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) = populateSetDate(hourOfDay, minute)

    @SuppressLint("SetTextI18n")
    private fun populateSetDate(hour: Int, minute: Int) {
        val hourStr = if (hour < 10) "0$hour" else hour.toString()
        val minuteStr = if (minute < 10) "0$minute" else minute.toString()

        hourText.setText(hourStr)
        minuteText.setText(minuteStr)

    }
}