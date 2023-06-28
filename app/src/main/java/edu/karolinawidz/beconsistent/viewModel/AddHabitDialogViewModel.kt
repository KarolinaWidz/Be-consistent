package edu.karolinawidz.beconsistent.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.karolinawidz.beconsistent.database.HabitDao
import edu.karolinawidz.beconsistent.database.HabitEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddHabitDialogViewModel(private val dao: HabitDao, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {
    private fun insertHabit(habit: HabitEntity) {
        viewModelScope.launch(dispatcher) {
            dao.insert(habit)
        }
    }

    fun addNewHabit(habitText: String) {
        val newHabit = HabitEntity(text = habitText)
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