package edu.karolinawidz.beconsistent.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.karolinawidz.beconsistent.database.Habit
import edu.karolinawidz.beconsistent.database.HabitDao
import kotlinx.coroutines.launch
import java.time.LocalDate

class AddHabitDialogViewModel(private val dao: HabitDao) : ViewModel() {
    private fun insertHabit(habit: Habit) {
        viewModelScope.launch {
            dao.insert(habit)
        }
    }

    fun addNewHabit(habitText: String) {
        val newHabit = Habit(text = habitText, lastUpdate = LocalDate.now())
        insertHabit(newHabit)
    }
}

class AddHabitDialogViewModelFactory(private val dao: HabitDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddHabitDialogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddHabitDialogViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}