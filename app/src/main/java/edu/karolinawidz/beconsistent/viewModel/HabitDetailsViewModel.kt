package edu.karolinawidz.beconsistent.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.karolinawidz.beconsistent.database.HabitDao
import edu.karolinawidz.beconsistent.database.model.Habit
import edu.karolinawidz.beconsistent.enums.GrowingStage
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitDetailsViewModel @Inject constructor(private val dao: HabitDao) : ViewModel() {

    fun getSpecificHabit(habitId: Int): LiveData<Habit> {
        return dao.getHabitById(habitId).asLiveData()
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch { dao.delete(habit) }
    }

    fun getDrawableForStreak(habit: Habit): Int {
        return GrowingStage.matchStreak(habit.streak).drawableId
    }

}