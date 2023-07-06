package edu.karolinawidz.beconsistent.database.util

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverter {

    @TypeConverter
    fun dateFromString(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun dateToString(date: LocalDate?): String? {
        return date?.toString()
    }
}