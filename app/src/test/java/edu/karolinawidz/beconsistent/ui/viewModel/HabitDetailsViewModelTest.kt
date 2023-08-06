package edu.karolinawidz.beconsistent.ui.viewModel

import androidx.lifecycle.Observer
import edu.karolinawidz.beconsistent.data.model.Habit
import edu.karolinawidz.beconsistent.util.InstantExecutorExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(InstantExecutorExtension::class)
internal class HabitDetailsViewModelTest : ViewModelTestBase() {
    private lateinit var viewModel: HabitDetailsViewModel

    @BeforeEach
    override fun setUp() {
        super.setUp()
        viewModel = HabitDetailsViewModel(fakeRepository)
    }

    @Test
    fun `delete habit successfully`() {
        val habitToDelete =
            Habit(2, "desc3", streak = 0, maxStreak = 20, lastChecked = LocalDate.MIN)

        viewModel.deleteHabit(habitToDelete)

        assertNull(fakeRepository.habits.find { habit -> habit == habitToDelete })
        assertEquals(4, fakeRepository.habits.size)
    }

    @Test
    fun `when deleting not-existing habit, nothing happens`() {
        val habitToDelete = Habit(Int.MAX_VALUE, "to delete")

        viewModel.deleteHabit(habitToDelete)

        assertNull(fakeRepository.habits.find { habit -> habit == habitToDelete })
        assertEquals(5, fakeRepository.habits.size)
    }

    @Test
    fun `find specific habit successfully`() {

        val specificHabitId = Int.MAX_VALUE
        val result = viewModel.getSpecificHabit(specificHabitId)

        val observer = Observer<Habit> {
            fail("Should not find habit")
        }
        result.observeForever(observer)
        result.removeObserver(observer)
    }
}