package edu.karolinawidz.beconsistent

import android.app.Application
import edu.karolinawidz.beconsistent.database.HabitDatabase

class BeConsistentApplication : Application() {
    val database: HabitDatabase by lazy { HabitDatabase.getDatabase(this) }
}