package com.dandev.storyapp.util.date

import com.dandev.storyapp.R
import java.text.SimpleDateFormat
import java.util.*

private const val SECOND = 1
private const val MINUTE = 60 * SECOND
private const val HOUR = 60 * MINUTE
private const val DAY = 24 * HOUR
private const val MONTH = 30 * DAY
private const val YEAR = 12 * MONTH

private fun currentDate(): Long {
    val calendar = Calendar.getInstance()
    return calendar.timeInMillis
}

// Long: time in millisecond
fun Long.toTimeAgo(): String {
    val time = this
    val now = currentDate()

    // convert back to second
    val diff = (now - time) / 1000

    return when {
        diff < MINUTE -> Strings.get(R.string.time_just_now)
        diff < 2 * MINUTE -> Strings.get(R.string.time_a_minute_ago)
        diff < 60 * MINUTE -> Strings.get(R.string.time_minutes_ago, diff / MINUTE)
        diff < 2 * HOUR -> Strings.get(R.string.time_an_hour_ago)
        diff < 24 * HOUR -> Strings.get(R.string.time_hours_ago, diff / HOUR)
        diff < 2 * DAY -> Strings.get(R.string.time_yesterday)
        diff < 30 * DAY -> Strings.get(R.string.time_days_ago, diff / DAY)
        diff < 2 * MONTH -> Strings.get(R.string.time_a_month_ago)
        diff < 12 * MONTH -> Strings.get(R.string.time_months_ago, diff / MONTH)
        diff < 2 * YEAR -> Strings.get(R.string.time_a_year_ago)
        else -> Strings.get(R.string.time_years_ago, diff / YEAR)
    }
}

fun getMilliseconds(date: String): Long {
    val localId = Locale("in", "ID")
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", localId)
    val getDate = format.parse(date)

    if (getDate != null) {
        return getDate.time
    }
    return 0
}