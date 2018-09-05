package com.alexeymerov.reddittesttask.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

enum class DateStringFormat(val stringFormat: String) {
    DD_MM_YYYY_SLASHED("dd/MM/yyyy")
}

fun Date.getString(stringFormat: DateStringFormat, isServerTime: Boolean = false): String {
    val simpleDateFormat = SimpleDateFormat(stringFormat.stringFormat, Locale.getDefault())
    if (isServerTime) simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
    return simpleDateFormat.format(this)
}

fun Date.getHourMinuteString(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)

fun Date.getPrettyDateString(): String = SimpleDateFormat("dd.MM.yy", Locale.getDefault()).format(this)

fun Date.getYearString(): String = SimpleDateFormat("yyyy", Locale.getDefault()).format(this)

fun Date.getDayString(): String {
    val todayCalendar = Calendar.getInstance()
    val todayDay = todayCalendar.get(Calendar.DAY_OF_MONTH)

    val dateCalendar = Calendar.getInstance()
    dateCalendar.time = this
    val dateDay = dateCalendar.get(Calendar.DAY_OF_MONTH)
    if (todayDay == dateDay) return "today"

    return SimpleDateFormat("dd MMMM", Locale.getDefault()).format(this)
}

fun differenceBetweenInSeconds(start: Date, end: Date): Int {
    val duration = end.time - start.time
    return TimeUnit.MILLISECONDS.toSeconds(duration).toInt()
}

fun differenceBetweenInMinutes(start: Date, end: Date): Int {
    val duration = end.time - start.time
    return TimeUnit.MILLISECONDS.toMinutes(duration).toInt()
}

fun differenceBetweenInHours(start: Date, end: Date): Int {
    val duration = end.time - start.time
    return TimeUnit.MILLISECONDS.toHours(duration).toInt()
}

fun differenceBetweenInDays(start: Date, end: Date): Int {
    val duration = end.time - start.time
    return TimeUnit.MILLISECONDS.toDays(duration).toInt()
}

fun differenceBetweenInMonths(start: Date, end: Date): Int {
    val duration = end.time - start.time
    val days = TimeUnit.MILLISECONDS.toDays(duration).toInt()
    return when {
        days > 30 -> days / 30
        else -> 0
    }
}