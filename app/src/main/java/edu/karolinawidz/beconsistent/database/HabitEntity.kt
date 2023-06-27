package edu.karolinawidz.beconsistent.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    var streak: Int = 0
//    val lastUpdate: Date
)
