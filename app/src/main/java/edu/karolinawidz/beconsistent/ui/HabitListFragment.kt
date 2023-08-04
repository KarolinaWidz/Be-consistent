package edu.karolinawidz.beconsistent.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import edu.karolinawidz.beconsistent.R
import edu.karolinawidz.beconsistent.broadcastReceiver.DateChangedReceiver
import edu.karolinawidz.beconsistent.database.model.Habit
import edu.karolinawidz.beconsistent.databinding.FragmentHabitListBinding
import edu.karolinawidz.beconsistent.ui.adapter.HabitRecyclerViewAdapter
import edu.karolinawidz.beconsistent.viewModel.HabitViewModel

@AndroidEntryPoint
class HabitListFragment : Fragment(R.layout.fragment_habit_list) {
    companion object {
        private const val TAG = "HabitListFragment"
    }

    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HabitViewModel by viewModels()
    private lateinit var dateChangedReceiver: DateChangedReceiver
    private lateinit var adapter: HabitRecyclerViewAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()
        dateChangedReceiver = DateChangedReceiver()
        dateChangedReceiver.dateChangedAction = {
            viewModel.clearAllBrokenStreaks()
            adapter.notifyDataSetChanged()
        }
        IntentFilter(Intent.ACTION_DATE_CHANGED).also {
            registerReceiver(requireContext(), dateChangedReceiver, it, RECEIVER_NOT_EXPORTED)
            Log.i(TAG, "receiver registered")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHabitListBinding.bind(view)
        binding.addHabit.setOnClickListener { showAddHabitDialog() }
        initList()
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

    private fun initList() {
        adapter = HabitRecyclerViewAdapter(requireContext())
        val animator = binding.recyclerView.itemAnimator as SimpleItemAnimator
        animator.supportsChangeAnimations = false
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        viewModel.allHabits.observe(viewLifecycleOwner) { adapter.submitList(it) }
        adapter.checkDoneItemClickListener = { checkDoneClick(it) }
        adapter.detailsItemClickListener = { goToDetails(it) }
        adapter.isHabitChecked = { viewModel.isHabitAlreadyChecked(it) }
        adapter.clearBrokenStreak = { viewModel.clearBrokenStreak(it) }
    }

    private fun showAddHabitDialog() {
        findNavController().navigate(R.id.addHabitDialogFragment)
    }

    private fun goToDetails(habit: Habit) {
        findNavController().navigate(
            HabitListFragmentDirections.actionHabitListFragmentToHabitDetails(
                habit.id
            )
        )
    }

    private fun checkDoneClick(habit: Habit) {
        viewModel.checkDoneHabit(habit)
        val toastContent = getString(R.string.habit_done, habit.text)
        Toast.makeText(activity, toastContent, Toast.LENGTH_SHORT).show()
    }
}