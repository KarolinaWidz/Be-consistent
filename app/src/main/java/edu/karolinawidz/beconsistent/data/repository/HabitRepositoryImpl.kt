package edu.karolinawidz.beconsistent.data.repository

import edu.karolinawidz.beconsistent.data.dao.HabitDao
import edu.karolinawidz.beconsistent.data.model.Habit
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(private val habitDao: HabitDao) : HabitRepository {

    override suspend fun insertHabit(habit: Habit) = habitDao.insert(habit)

    override suspend fun updateHabit(habit: Habit) = habitDao.update(habit)

    override suspend fun deleteHabit(habit: Habit) = habitDao.delete(habit)

    override fun getHabitById(id: Int) = habitDao.getHabitById(id)

    override fun getAllHabits(): Flow<List<Habit>> = habitDao.getAll()
}