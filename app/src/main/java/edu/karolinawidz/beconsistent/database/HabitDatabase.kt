package edu.karolinawidz.beconsistent.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.karolinawidz.beconsistent.database.util.DateConverter

@Database(entities = [Habit::class], version = 4)
@TypeConverters(DateConverter::class)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun dao(): HabitDao

    companion object {
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getDatabase(context: Context): HabitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "habit_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}