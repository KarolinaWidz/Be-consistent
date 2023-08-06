package edu.karolinawidz.beconsistent.ui.viewModel

import edu.karolinawidz.beconsistent.data.model.Habit
import edu.karolinawidz.beconsistent.util.InstantExecutorExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(InstantExecutorExtension::class)
internal class HabitViewModelTest : ViewModelTestBase() {

    private lateinit var viewModel: HabitViewModel

    @BeforeEach
    fun setupLiveData() {
        viewModel = HabitViewModel(fakeRepository)
        viewModel.allHabits.observeForever { }
    }

    @AfterEach
    fun tearDownLiveData() {
        viewModel.allHabits.removeObserver { }
    }

    @Test
    fun `when habit checked done first time, habit parameters updated successfully`() {
        val habitToUpdate = Habit(0, "desc1")

        viewModel.checkDoneHabit(habitToUpdate)

        assertEquals("desc1", fakeRepository.habits[0].text)
        assertEquals(LocalDate.now(), fakeRepository.habits[0].lastChecked)
        assertEquals(1, fakeRepository.habits[0].streak)
        assertEquals(1, fakeRepository.habits[0].maxStreak)
    }

    @Test
    fun `when habit checked done second time, habit parameters not updated`() {

        val habitToUpdate =
            Habit(1, "desc2", streak = 1, lastChecked = LocalDate.now(), maxStreak = 1)

        viewModel.checkDoneHabit(habitToUpdate)

        assertEquals("desc2", fakeRepository.habits[1].text)
        assertEquals(LocalDate.now(), fakeRepository.habits[1].lastChecked)
        assertEquals(1, fakeRepository.habits[1].streak)
        assertEquals(1, fakeRepository.habits[1].maxStreak)
    }

    @Test
    fun `when habits have cleared broken stroke, only old ones have been cleared`() {

        viewModel.clearAllBrokenStreaks()
        assertEquals("desc1", fakeRepository.habits[0].text)
        assertEquals(LocalDate.MIN, fakeRepository.habits[0].lastChecked)
        assertEquals(0, fakeRepository.habits[0].streak)
        assertEquals(0, fakeRepository.habits[0].maxStreak)

        assertEquals("desc2", fakeRepository.habits[1].text)
        assertEquals(LocalDate.now(), fakeRepository.habits[1].lastChecked)
        assertEquals(1, fakeRepository.habits[1].streak)
        assertEquals(1, fakeRepository.habits[1].maxStreak)

        assertEquals("desc3", fakeRepository.habits[2].text)
        assertEquals(LocalDate.MIN, fakeRepository.habits[2].lastChecked)
        assertEquals(0, fakeRepository.habits[2].streak)
        assertEquals(20, fakeRepository.habits[2].maxStreak)

        assertEquals("desc4", fakeRepository.habits[3].text)
        assertEquals(LocalDate.MIN, fakeRepository.habits[3].lastChecked)
        assertEquals(0, fakeRepository.habits[3].streak)
        assertEquals(10, fakeRepository.habits[3].maxStreak)

        assertEquals("desc5", fakeRepository.habits[4].text)
        assertEquals(LocalDate.now().minusDays(1), fakeRepository.habits[4].lastChecked)
        assertEquals(2, fakeRepository.habits[4].streak)
        assertEquals(2, fakeRepository.habits[4].maxStreak)
    }
}