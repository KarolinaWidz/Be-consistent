package edu.karolinawidz.beconsistent.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.karolinawidz.beconsistent.data.model.Habit
import edu.karolinawidz.beconsistent.data.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditHabitDialogViewModel @Inject constructor(private val repository: HabitRepository) :
    ViewModel() {

    private fun getSpecificHabit(habitId: Int): Flow<Habit> {
        return repository.getHabitById(habitId)
    }

    private fun updateHabit(habit: Habit) {
        viewModelScope.launch { repository.updateHabit(habit) }
    }

    fun editHabit(habitId: Int, editedHabitText: String) {
        viewModelScope.launch {
            getSpecificHabit(habitId).collect {
                val editedHabit = it.copy(text = editedHabitText)
                updateHabit(editedHabit)
            }
        }
    }

}