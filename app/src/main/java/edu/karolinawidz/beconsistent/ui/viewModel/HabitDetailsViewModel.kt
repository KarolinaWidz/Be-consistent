package edu.karolinawidz.beconsistent.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.karolinawidz.beconsistent.data.model.Habit
import edu.karolinawidz.beconsistent.data.repository.HabitRepository
import edu.karolinawidz.beconsistent.enums.GrowingStage
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitDetailsViewModel @Inject constructor(private val repository: HabitRepository) :
    HabitBaseViewModel(repository) {

    fun getSpecificHabit(habitId: Int): LiveData<Habit> {
        return repository.getHabitById(habitId).asLiveData()
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch { repository.deleteHabit(habit) }
    }

    fun getDrawableForStreak(habit: Habit): Int {
        return GrowingStage.matchStreak(habit.streak).drawableId
    }

}