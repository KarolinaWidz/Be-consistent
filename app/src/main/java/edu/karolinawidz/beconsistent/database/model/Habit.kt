package edu.karolinawidz.beconsistent.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "habit")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val streak: Int = 0,
    val lastUpdate: LocalDate = LocalDate.MIN
)
