package edu.karolinawidz.beconsistent.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import edu.karolinawidz.beconsistent.BeConsistentApplication
import edu.karolinawidz.beconsistent.R
import edu.karolinawidz.beconsistent.viewModel.AddHabitDialogViewModel
import edu.karolinawidz.beconsistent.viewModel.AddHabitDialogViewModelFactory


class AddHabitDialogFragment : DialogFragment() {
    private val addHabitViewModel: AddHabitDialogViewModel by viewModels {
        AddHabitDialogViewModelFactory((requireActivity().application as BeConsistentApplication).database.dao())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it).apply {
                setTitle(R.string.add_habit)
                setView(R.layout.fragment_add_habit_dialog)
                setPositiveButton(R.string.ok) { _, _ -> addHabitViewModel.addNewHabit("test value") }
                setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
            }
            builder.create()
        } ?: throw IllegalStateException("Null activity")
    }
}