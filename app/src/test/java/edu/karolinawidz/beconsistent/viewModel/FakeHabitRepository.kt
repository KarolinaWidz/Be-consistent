package edu.karolinawidz.beconsistent.viewModel

import edu.karolinawidz.beconsistent.data.model.Habit
import edu.karolinawidz.beconsistent.data.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeHabitRepository(var habits: MutableList<Habit>) : HabitRepository {
    override suspend fun insertHabit(habit: Habit) {
        habits.add(habit)
    }

    override suspend fun updateHabit(habit: Habit) {
        val oldHabit = habits.find { it.id == habit.id }
        oldHabit?.let {
            habits = habits.replace(oldHabit, habit)
        }
    }

    override suspend fun deleteHabit(habit: Habit) {
        habits.remove(habit)
    }

    override fun getHabitById(id: Int): Flow<Habit> {
        return flow { habits.find { it.id == id } }
    }

    override fun getAllHabits(): Flow<List<Habit>> {
        return flow { habits }
    }

    private fun MutableList<Habit>.replace(old: Habit, new: Habit) =
        map { if (it == old) new else it }.toMutableList()
}