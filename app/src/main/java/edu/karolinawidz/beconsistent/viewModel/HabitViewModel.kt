package edu.karolinawidz.beconsistent.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import edu.karolinawidz.beconsistent.database.Habit
import edu.karolinawidz.beconsistent.database.HabitDao


class HabitViewModel(dao: HabitDao) : ViewModel() {

    val allHabits: LiveData<List<Habit>> = dao.getAll().asLiveData()

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