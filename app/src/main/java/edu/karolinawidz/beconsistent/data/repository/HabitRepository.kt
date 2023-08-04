package edu.karolinawidz.beconsistent.data.repository

import edu.karolinawidz.beconsistent.data.dao.HabitDao
import edu.karolinawidz.beconsistent.data.model.Habit
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class HabitRepository @Inject constructor(private val habitDao: HabitDao) {

    suspend fun insertHabit(habit: Habit) = habitDao.insert(habit)

    suspend fun updateHabit(habit: Habit) = habitDao.update(habit)

    suspend fun deleteHabit(habit: Habit) = habitDao.delete(habit)

    fun getHabitById(id: Int) = habitDao.getHabitById(id)

    fun getAllHabits(): Flow<List<Habit>> = habitDao.getAll()
}