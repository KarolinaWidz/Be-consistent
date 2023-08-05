package edu.karolinawidz.beconsistent.data.repository

import edu.karolinawidz.beconsistent.data.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    suspend fun insertHabit(habit: Habit)
    suspend fun updateHabit(habit: Habit)
    suspend fun deleteHabit(habit: Habit)
    fun getHabitById(id: Int): Flow<Habit>
    fun getAllHabits(): Flow<List<Habit>>
}