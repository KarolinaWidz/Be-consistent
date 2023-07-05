package edu.karolinawidz.beconsistent.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import edu.karolinawidz.beconsistent.database.Habit
import edu.karolinawidz.beconsistent.database.HabitDao
import kotlinx.coroutines.launch
import java.time.LocalDate


class HabitViewModel(private val dao: HabitDao) : ViewModel() {

    val allHabits: LiveData<List<Habit>> = dao.getAll().asLiveData()

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch { dao.delete(habit) }
    }

    fun checkDoneHabit(habit: Habit) {
        if (!isHabitCheckDoneToday(habit)) {
            val updatedHabit = habit.copy(streak = habit.streak + 1, lastUpdate = LocalDate.now())
            viewModelScope.launch { dao.update(updatedHabit) }
        }
    }

    private fun isHabitCheckDoneToday(habit: Habit) = habit.lastUpdate.isEqual(LocalDate.now())
}

class HabitViewModelFactory(private val dao: HabitDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}