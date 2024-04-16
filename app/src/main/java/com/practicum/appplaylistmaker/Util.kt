package com.practicum.appplaylistmaker

import android.content.Context
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Int.dpToPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this * density).toInt()
}

fun MillisecondsToHumanReadable(time: Int): String {
    return SimpleDateFormat("mm:ss", Locale.getDefault()).format(time)
}

