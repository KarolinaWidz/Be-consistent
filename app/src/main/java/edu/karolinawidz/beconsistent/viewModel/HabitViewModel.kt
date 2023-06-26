package edu.karolinawidz.beconsistent.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.karolinawidz.beconsistent.database.HabitDao
import java.lang.IllegalArgumentException

class HabitViewModel(private val dao: HabitDao) : ViewModel() {

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