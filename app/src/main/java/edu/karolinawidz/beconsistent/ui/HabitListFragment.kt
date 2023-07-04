package edu.karolinawidz.beconsistent.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import edu.karolinawidz.beconsistent.BeConsistentApplication
import edu.karolinawidz.beconsistent.R
import edu.karolinawidz.beconsistent.databinding.FragmentHabitListBinding
import edu.karolinawidz.beconsistent.viewModel.HabitViewModel
import edu.karolinawidz.beconsistent.viewModel.HabitViewModelFactory

class HabitListFragment : Fragment(R.layout.fragment_habit_list) {
    companion object {
        const val TAG = "HabitListFragment"
    }

    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HabitViewModel by viewModels {
        HabitViewModelFactory((activity?.application as BeConsistentApplication).database.dao())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHabitListBinding.bind(view)
        initList()
        binding.addHabit.setOnClickListener { showAddHabitDialog() }
    }

    private fun initList() {
        val adapter = HabitRecyclerViewAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        viewModel.allHabits.observe(viewLifecycleOwner) { habits ->
            habits.let { adapter.submitList(it) }
        }
        adapter.deleteItemClickListener = { habit -> viewModel.deleteHabit(habit) }
    }

    private fun showAddHabitDialog() {
        val dialog = AddHabitDialogFragment()
        dialog.show(requireActivity().supportFragmentManager, TAG)
    }
}