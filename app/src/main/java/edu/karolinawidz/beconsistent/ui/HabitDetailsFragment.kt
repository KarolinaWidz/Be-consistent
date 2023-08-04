package edu.karolinawidz.beconsistent.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.karolinawidz.beconsistent.R
import edu.karolinawidz.beconsistent.broadcastReceiver.DateChangedReceiver
import edu.karolinawidz.beconsistent.data.model.Habit
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
    private lateinit var dateChangedReceiver: DateChangedReceiver
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

    override fun onStart() {
        super.onStart()
        dateChangedReceiver = DateChangedReceiver()
        dateChangedReceiver.dateChangedAction = { viewModel.clearBrokenStreak(habit) }
        IntentFilter(Intent.ACTION_DATE_CHANGED).also {
            registerReceiver(requireContext(), dateChangedReceiver, it, RECEIVER_NOT_EXPORTED)
            Log.i(TAG, "receiver registered")
        }
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(dateChangedReceiver)
        Log.i(TAG, "receiver unregistered")
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