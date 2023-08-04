package edu.karolinawidz.beconsistent.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.karolinawidz.beconsistent.database.HabitDao
import edu.karolinawidz.beconsistent.database.model.Habit
import kotlinx.coroutines.launch
import java.time.LocalDate

abstract class HabitBaseViewModel(private val dao: HabitDao) : ViewModel() {

    fun clearBrokenStreak(habit: Habit) {
        if (isStreakBroken(habit)) {
            val updatedHabit = habit.copy(streak = 0, lastUpdate = LocalDate.MIN)
            viewModelScope.launch { dao.update(updatedHabit) }
        }
    }

    fun isHabitAlreadyChecked(habit: Habit) = habit.lastUpdate.isEqual(LocalDate.now())

    private fun isStreakBroken(habit: Habit) =
        !habit.lastUpdate.isEqual(LocalDate.MIN) && habit.lastUpdate.isBefore(
            LocalDate.now().minusDays(1)
        )
}