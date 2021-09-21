package com.niyazi.news.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.format(pattern: String, wantedPattern: String): String? {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.ROOT)
    val wantedDateFormat = SimpleDateFormat(wantedPattern, Locale.ROOT)
    return try {
        val date = simpleDateFormat.parse(this)
        date?.let {
            wantedDateFormat.format(date)
        }
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}

fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
        .show()
}