package edu.karolinawidz.beconsistent.ui.viewModel

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class EditHabitDialogViewModelTest : ViewModelTestBase() {
    private lateinit var viewModel: EditHabitDialogViewModel

    @BeforeEach
    override fun setUp() {
        super.setUp()
        viewModel = EditHabitDialogViewModel(fakeRepository)
    }

    @Test
    fun `edit existing habit successfully`() {
        val editedHabitDescription = "edited desc2"

        viewModel.editHabit(1, editedHabitDescription)

        val editedHabit =
            fakeRepository.habits.find { habit -> habit.text == editedHabitDescription }
        assertNotNull(editedHabit)

        if (editedHabit != null) {
            assertEquals(editedHabitDescription, editedHabit.text)
            assertEquals(LocalDate.now(), editedHabit.lastChecked)
            assertEquals(1, editedHabit.streak)
            assertEquals(1, editedHabit.maxStreak)
        } else {
            fail("Habit has been not updated")
        }
    }

    @Test
    fun `when editing not-existing habit, nothing happens`() {
        val editedHabitDescription = "edited desc"

        viewModel.editHabit(Int.MAX_VALUE, editedHabitDescription)

        val editedHabit =
            fakeRepository.habits.find { habit -> habit.text == editedHabitDescription }

        assertNull(editedHabit)
    }
}