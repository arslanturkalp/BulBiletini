package com.alparslanturk.kombineapp.utils

import com.alparslanturk.kombineapp.data.entities.enums.DateFormatType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toString(dateFormatType: DateFormatType, locale: Locale = Locale("tr")): String = SimpleDateFormat(dateFormatType.format, locale).format(this)
