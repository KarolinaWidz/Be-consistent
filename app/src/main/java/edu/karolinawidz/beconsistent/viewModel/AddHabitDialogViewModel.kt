package edu.karolinawidz.beconsistent.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.karolinawidz.beconsistent.database.Habit
import edu.karolinawidz.beconsistent.database.HabitDao
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddHabitDialogViewModel @Inject constructor(private val dao: HabitDao) : ViewModel() {
    private fun insertHabit(habit: Habit) {
        viewModelScope.launch {
            dao.insert(habit)
        }
    }

    fun addNewHabit(habitText: String) {
        val newHabit = Habit(text = habitText)
        insertHabit(newHabit)
    }
}