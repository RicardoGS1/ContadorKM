package com.virtualworld.contadorkm.domain.utils

import java.util.Calendar
import java.util.Date

object DateUtils {
    fun getFirstDayOfMonth(): Date
    {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar.time
    }

    fun getFirstDayOfCurrentYear(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, Calendar.JANUARY)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        return calendar.time
    }

    fun getFirstDayOfWeek(): Date {
        val calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        return calendar.time
    }
}