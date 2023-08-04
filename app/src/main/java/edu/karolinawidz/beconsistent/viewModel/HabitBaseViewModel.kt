package edu.karolinawidz.beconsistent.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.karolinawidz.beconsistent.data.model.Habit
import edu.karolinawidz.beconsistent.data.repository.HabitRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

abstract class HabitBaseViewModel(private val repository: HabitRepository) : ViewModel() {

    fun clearBrokenStreak(habit: Habit) {
        if (isStreakBroken(habit)) {
            val updatedHabit = habit.copy(streak = 0, lastUpdate = LocalDate.MIN)
            viewModelScope.launch { repository.updateHabit(updatedHabit) }
        }
    }

    fun isHabitAlreadyChecked(habit: Habit) = habit.lastUpdate.isEqual(LocalDate.now())

    private fun isStreakBroken(habit: Habit) =
        !habit.lastUpdate.isEqual(LocalDate.MIN) && habit.lastUpdate.isBefore(
            LocalDate.now().minusDays(1)
        )
}