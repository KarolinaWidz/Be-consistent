package edu.karolinawidz.beconsistent.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.karolinawidz.beconsistent.data.model.Habit
import edu.karolinawidz.beconsistent.data.repository.HabitRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddHabitDialogViewModel @Inject constructor(private val repository: HabitRepository) :
    ViewModel() {
    private fun insertHabit(habit: Habit) {
        viewModelScope.launch {
            repository.insertHabit(habit)
        }
    }

    fun addNewHabit(habitText: String) {
        val newHabit = Habit(text = habitText)
        insertHabit(newHabit)
    }
}