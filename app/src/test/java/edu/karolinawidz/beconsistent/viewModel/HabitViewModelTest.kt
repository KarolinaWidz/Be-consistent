package edu.karolinawidz.beconsistent.viewModel

import edu.karolinawidz.beconsistent.data.model.Habit
import edu.karolinawidz.beconsistent.ui.viewModel.HabitViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate


internal class HabitViewModelTest {
    private lateinit var fakeRepository: FakeHabitRepository
    private lateinit var viewModel: HabitViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)

        val habits = mutableListOf(
            Habit(0, "desc1"),
            Habit(1, "desc2"),
            Habit(2, "desc3")
        )
        fakeRepository = FakeHabitRepository(habits)
        viewModel = HabitViewModel(fakeRepository)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when habit checked done, habit parameters updated successfully`() {
        val habitToUpdate = Habit(0, "desc1")

        viewModel.checkDoneHabit(habitToUpdate)
        assertEquals(LocalDate.now(), fakeRepository.habits[0].lastUpdate)
        assertEquals(1, fakeRepository.habits[0].streak)
        assertEquals(1, fakeRepository.habits[0].maxStreak)
    }
}