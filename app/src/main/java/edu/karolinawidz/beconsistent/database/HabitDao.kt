package edu.karolinawidz.beconsistent.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Insert
    suspend fun insert(habit: HabitEntity)

    @Delete
    suspend fun delete(habit: HabitEntity)

    @Query("SELECT * from habit")
    fun getAll(): Flow<List<HabitEntity>>

    @Query("SELECT * from habit WHERE id = :id")
    fun getHabitById(id:Int):Flow<HabitEntity>

}