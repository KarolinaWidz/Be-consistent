package edu.karolinawidz.beconsistent.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.karolinawidz.beconsistent.R
import edu.karolinawidz.beconsistent.database.model.Habit
import edu.karolinawidz.beconsistent.databinding.FragmentHabitDetailsBinding
import edu.karolinawidz.beconsistent.ui.dialogs.DeleteHabitDialogFragment
import edu.karolinawidz.beconsistent.viewModel.HabitDetailsViewModel

@AndroidEntryPoint
class HabitDetailsFragment : Fragment(R.layout.fragment_habit_details) {
    companion object {
        private const val HABIT_ID_KEY = "habitId"
        private const val TAG = "HabitDetailsFragment"
    }

    private var _binding: FragmentHabitDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HabitDetailsViewModel by viewModels()
    private var habitId = 0
    private lateinit var habit: Habit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            habitId = it.getInt(HABIT_ID_KEY)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHabitDetailsBinding.bind(view)
        viewModel.getSpecificHabit(habitId).observe(viewLifecycleOwner) {
            habit = it
            bind(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bind(habit: Habit) {
        binding.habitText.text = habit.text
        binding.streakValue.text = habit.streak.toString()
        binding.maxStreakValue.text = habit.maxStreak.toString()
        binding.deleteBtn.setOnClickListener { showDeleteHabitDialog(habit) }
        binding.editBtn.setOnClickListener { editHabit() }
        binding.habitImageView.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                viewModel.getDrawableForStreak(habit),
                null
            )
        )
    }

    private fun showDeleteHabitDialog(habit: Habit) {
        val dialog = DeleteHabitDialogFragment()
        dialog.deleteHabitListener = { deleteHabit(habit) }
        dialog.show(requireActivity().supportFragmentManager, TAG)
    }

    private fun deleteHabit(habit: Habit) {
        viewModel.deleteHabit(habit)
        findNavController().navigate(R.id.habitListFragment)
    }

    private fun editHabit() {
        findNavController().navigate(
            HabitDetailsFragmentDirections.actionHabitDetailsFragmentToEditHabitDialogFragment(
                habit.id
            )
        )
    }
}