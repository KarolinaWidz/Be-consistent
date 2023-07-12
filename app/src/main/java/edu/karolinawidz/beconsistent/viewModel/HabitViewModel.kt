package edu.karolinawidz.beconsistent.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.karolinawidz.beconsistent.database.Habit
import edu.karolinawidz.beconsistent.database.HabitDao
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(private val dao: HabitDao) : ViewModel() {

    val allHabits: LiveData<List<Habit>> = dao.getAll().asLiveData()

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch { dao.delete(habit) }
    }

    fun checkDoneHabit(habit: Habit) {
        if (!isHabitAlreadyChecked(habit)) {
            val updatedHabit = habit.copy(streak = habit.streak + 1, lastUpdate = LocalDate.now())
            viewModelScope.launch { dao.update(updatedHabit) }
        }
    }

    private fun isHabitAlreadyChecked(habit: Habit) = habit.lastUpdate.isEqual(LocalDate.now())
}