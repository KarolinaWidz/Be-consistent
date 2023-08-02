package edu.karolinawidz.beconsistent.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.karolinawidz.beconsistent.R

class DeleteHabitDialogFragment : DialogFragment() {
    lateinit var deleteHabitListener: () -> Unit
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it).apply {
                setTitle(R.string.delete_habit_confirmation)
                setPositiveButton(R.string.yes) { _, _ -> deleteHabitListener() }
                setNegativeButton(R.string.no) { dialog, _ -> dialog.cancel() }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}