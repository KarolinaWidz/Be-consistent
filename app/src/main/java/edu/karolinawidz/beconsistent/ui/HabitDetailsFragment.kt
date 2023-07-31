package edu.karolinawidz.beconsistent.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.karolinawidz.beconsistent.R
import edu.karolinawidz.beconsistent.database.model.Habit
import edu.karolinawidz.beconsistent.databinding.FragmentHabitDetailsBinding

import edu.karolinawidz.beconsistent.viewModel.HabitDetailsViewModel

@AndroidEntryPoint
class HabitDetailsFragment : Fragment(R.layout.fragment_habit_details) {
    private var _binding: FragmentHabitDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HabitDetailsViewModel by viewModels()
    private var habitId = 0
    private lateinit var habit: Habit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            habitId = it.getInt("habitId")
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
        binding.deleteBtn.setOnClickListener { deleteHabit(habit) }
    }

    private fun deleteHabit(habit: Habit) {
        viewModel.deleteHabit(habit)
        findNavController().navigate(R.id.habitListFragment)
    }

}