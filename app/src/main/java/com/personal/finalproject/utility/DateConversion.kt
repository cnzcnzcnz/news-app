package com.personal.finalproject.utility

import android.annotation.SuppressLint
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

object DateConversion {
    @SuppressLint("SimpleDateFormat")
    fun convertDateToText(source: String?): String?{
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val convert = inputFormat.parse(source)
        return DateUtils.getRelativeTimeSpanString(convert.time, Calendar.getInstance().timeInMillis, DateUtils.MINUTE_IN_MILLIS).toString()
    }

}