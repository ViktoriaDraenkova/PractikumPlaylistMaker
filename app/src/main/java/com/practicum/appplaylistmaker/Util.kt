package com.practicum.appplaylistmaker

import android.content.Context

fun Int.dpToPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this * density).toInt()
}
