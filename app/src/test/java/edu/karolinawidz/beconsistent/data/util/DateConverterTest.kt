package edu.karolinawidz.beconsistent.data.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class DateConverterTest {

    private val converter = DateConverter()

    @Test
    fun `convert date to string`() {
        val dateToConvert = LocalDate.of(2023, 8, 6)
        val result = converter.dateToString(dateToConvert)
        assertEquals("2023-08-06", result)
    }

    @Test
    fun `convert date from string`() {
        val dateToConvert = "2023-08-06"
        val result = converter.dateFromString(dateToConvert)
        assertEquals(LocalDate.of(2023, 8, 6), result)
    }
}