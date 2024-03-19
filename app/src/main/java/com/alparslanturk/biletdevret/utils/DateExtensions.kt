package com.alparslanturk.biletdevret.utils

import com.alparslanturk.biletdevret.data.entities.enums.DateFormatType
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toString(dateFormatType: DateFormatType, locale: Locale = Locale("tr")): String = SimpleDateFormat(dateFormatType.format, locale).format(this)

fun String.toDate(locale: Locale = Locale("tr"), dateFormatType: DateFormatType): Date? = try {
    SimpleDateFormat(dateFormatType.format, locale).parse(this)
} catch (e: ParseException) {
    e.printStackTrace()
    null
}