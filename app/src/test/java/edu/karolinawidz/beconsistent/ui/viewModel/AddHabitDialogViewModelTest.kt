package edu.karolinawidz.beconsistent.ui.viewModel

import edu.karolinawidz.beconsistent.util.InstantExecutorExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class AddHabitDialogViewModelTest : ViewModelTestBase() {

    private lateinit var viewModel: AddHabitDialogViewModel

    @BeforeEach
    override fun setUp() {
        super.setUp()
        viewModel = AddHabitDialogViewModel(fakeRepository)
    }

    @Test
    fun `new habit add successfully`() {
        val newHabitText = "New habit"

        viewModel.addNewHabit(newHabitText)

        assertNotNull(fakeRepository.habits.find { habit -> habit.text == newHabitText })
    }
}