package com.alan.core.utils

import android.content.Context
import com.alan.core.R

fun formatDate(context: Context, publishedDate: String): String {
    val month = publishedDate.substringAfter("-").substringBeforeLast("-").toInt()
    val day = publishedDate.substringAfterLast("-").toInt()
    val months = context.resources.getStringArray(R.array.months)

    return context.getString(R.string.published_date_format, day, months[month - 1])
}

fun formatVacancyCount(context: Context, count: Int): String {
    return "$count " + when {
        count % 10 == 1 && count % 100 != 11 -> context.getString(R.string.vacancy_singular)
        count % 10 in 2..4 && count % 100 !in 12..14 -> context.getString(R.string.vacancy_plural_two_to_four)
        else -> context.getString(R.string.vacancy_plural_other)
    }
}

fun formatPersonCount(context: Context, count: Int): String {
    return when {
        count % 10 == 1 && count % 100 != 11 -> context.getString(R.string.person_singular)
        count % 10 in 2..4 && count % 100 !in 12..14 -> context.getString(R.string.person_plural_two_to_four)
        else -> context.getString(R.string.person_plural_other)
    }
}
