package edu.karolinawidz.beconsistent.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "habit")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val streak: Int = 0,
    val lastChecked: LocalDate = LocalDate.MIN,
    val maxStreak: Int = 0
)
