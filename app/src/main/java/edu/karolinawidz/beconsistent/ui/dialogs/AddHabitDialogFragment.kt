package edu.karolinawidz.beconsistent.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import edu.karolinawidz.beconsistent.R
import edu.karolinawidz.beconsistent.ui.viewModel.AddHabitDialogViewModel

@AndroidEntryPoint
class AddHabitDialogFragment : DialogFragment() {
    private val addHabitViewModel: AddHabitDialogViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it).apply {
                setTitle(R.string.add_habit)
                setView(R.layout.fragment_add_habit_dialog)
                setPositiveButton(R.string.ok) { _, _ -> addHabit() }
                setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
            }
            builder.create()
        } ?: throw IllegalStateException("Null activity")
    }

    private fun addHabit() {
        val passedText =
            dialog?.findViewById<TextInputLayout>(R.id.new_habit_description)?.editText?.text
        if (passedText != null && passedText.toString().isNotBlank()) {
            addHabitViewModel.addNewHabit(passedText.toString())
            Toast.makeText(activity, R.string.successfully_added, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, R.string.operation_unsuccessful, Toast.LENGTH_SHORT).show()
        }

    }
}