package edu.karolinawidz.beconsistent

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import edu.karolinawidz.beconsistent.database.HabitDatabase

@HiltAndroidApp
class BeConsistentApplication : Application() {
    val database: HabitDatabase by lazy { HabitDatabase.getDatabase(this) }
}