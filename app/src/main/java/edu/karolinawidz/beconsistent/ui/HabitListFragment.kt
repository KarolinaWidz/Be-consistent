package edu.karolinawidz.beconsistent.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import edu.karolinawidz.beconsistent.DateChangedReceiver
import edu.karolinawidz.beconsistent.R
import edu.karolinawidz.beconsistent.databinding.FragmentHabitListBinding
import edu.karolinawidz.beconsistent.ui.adapter.HabitRecyclerViewAdapter
import edu.karolinawidz.beconsistent.viewModel.HabitViewModel

@AndroidEntryPoint
class HabitListFragment : Fragment(R.layout.fragment_habit_list) {
    companion object {
        const val TAG = "HabitListFragment"
    }

    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HabitRecyclerViewAdapter
    private val viewModel: HabitViewModel by viewModels()
    private lateinit var dateChangedReceiver: DateChangedReceiver

    override fun onStart() {
        super.onStart()
        dateChangedReceiver = DateChangedReceiver()
        IntentFilter(Intent.ACTION_DATE_CHANGED).also {
            registerReceiver(requireContext(), dateChangedReceiver, it, RECEIVER_NOT_EXPORTED)
        }
        dateChangedReceiver.dateChangedAction = { viewModel.clearAllBrokenStreaks() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHabitListBinding.bind(view)
        initList()
        binding.addHabit.setOnClickListener { showAddHabitDialog() }
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(dateChangedReceiver)
    }

    private fun initList() {
        adapter = HabitRecyclerViewAdapter(requireContext())
        val animator = binding.recyclerView.itemAnimator as SimpleItemAnimator
        animator.supportsChangeAnimations = false
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        viewModel.allHabits.observe(viewLifecycleOwner) { adapter.submitList(it) }
        adapter.deleteItemClickListener = { habit -> viewModel.deleteHabit(habit) }
        adapter.checkDoneItemClickListener = { habit -> viewModel.checkDoneHabit(habit) }
        adapter.isHabitChecked = { habit -> viewModel.isHabitAlreadyChecked(habit) }
        adapter.clearBrokenStreak = { habit -> viewModel.clearBrokenStreak(habit) }
    }

    private fun showAddHabitDialog() {
        val dialog = AddHabitDialogFragment()
        dialog.show(requireActivity().supportFragmentManager, TAG)
    }
}