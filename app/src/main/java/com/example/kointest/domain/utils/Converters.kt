package com.example.kointest.domain.utils

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateTypeConverters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {

        return when (value) {
            null -> null
            else -> Date(value)
        }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {

        return when (date) {
            null -> null
            else -> date.time
        }
    }
}