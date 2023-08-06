package edu.karolinawidz.beconsistent.ui.viewModel

import android.util.Log
import edu.karolinawidz.beconsistent.data.model.Habit
import edu.karolinawidz.beconsistent.repository.FakeHabitRepository
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDate

abstract class ViewModelTestBase {

    protected lateinit var fakeRepository: FakeHabitRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    open fun setUp() {
        Dispatchers.setMain(testDispatcher)

        val habits = mutableListOf(
            Habit(0, "desc1"),
            Habit(1, "desc2", streak = 1, maxStreak = 1, lastChecked = LocalDate.now()),
            Habit(2, "desc3", streak = 0, maxStreak = 20, lastChecked = LocalDate.MIN),
            Habit(
                3,
                "desc4",
                streak = 5,
                maxStreak = 10,
                lastChecked = LocalDate.now().minusDays(2)
            ),
            Habit(
                4,
                "desc5",
                streak = 2,
                maxStreak = 2,
                lastChecked = LocalDate.now().minusDays(1)
            ),
        )
        fakeRepository = FakeHabitRepository(habits)

        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }
}