package edu.karolinawidz.beconsistent.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.karolinawidz.beconsistent.database.HabitDao
import edu.karolinawidz.beconsistent.database.model.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditHabitDialogViewModel @Inject constructor(private val dao: HabitDao) : ViewModel() {

    private fun getSpecificHabit(habitId: Int): Flow<Habit> {
        return dao.getHabitById(habitId)
    }

    private fun updateHabit(habit: Habit) {
        viewModelScope.launch { dao.update(habit) }
    }

    fun editHabit(habitId: Int, editedHabitText: String) {
        viewModelScope.launch {
            getSpecificHabit(habitId).collect{
                val editedHabit = it.copy(text = editedHabitText)
                updateHabit(editedHabit)
            }
        }
    }

}