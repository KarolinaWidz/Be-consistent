package edu.karolinawidz.beconsistent.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.karolinawidz.beconsistent.data.model.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Insert
    suspend fun insert(habit: Habit)

    @Delete
    suspend fun delete(habit: Habit)

    @Query("SELECT * from habit")
    fun getAll(): Flow<List<Habit>>

    @Query("SELECT * from habit WHERE id = :id")
    fun getHabitById(id:Int):Flow<Habit>

    @Update
    suspend fun update(habit: Habit)

}