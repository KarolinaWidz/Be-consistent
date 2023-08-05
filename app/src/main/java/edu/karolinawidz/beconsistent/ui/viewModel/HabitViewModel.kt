package edu.karolinawidz.beconsistent.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.karolinawidz.beconsistent.data.model.Habit
import edu.karolinawidz.beconsistent.data.repository.HabitRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
class HabitViewModel @Inject constructor(private val repository: HabitRepository) :
    HabitBaseViewModel(repository) {
    companion object {
        private const val TAG = "HabitViewModel"
    }

    val allHabits: LiveData<List<Habit>> = repository.getAllHabits().asLiveData()

    fun clearAllBrokenStreaks() {
        allHabits.value?.forEach { clearBrokenStreak(it) }
        Log.i(TAG, "Broken streaks have been cleared")
    }

    fun checkDoneHabit(habit: Habit) {
        if (!isHabitAlreadyChecked(habit)) {
            val updatedHabit = habit.copy(
                streak = habit.streak + 1,
                lastUpdate = LocalDate.now(),
                maxStreak = max(habit.maxStreak, habit.streak + 1)
            )
            viewModelScope.launch { repository.updateHabit(updatedHabit) }
        }
    }


}