package edu.karolinawidz.beconsistent.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.karolinawidz.beconsistent.R
import edu.karolinawidz.beconsistent.database.model.Habit
import edu.karolinawidz.beconsistent.databinding.HabitItemBinding


class HabitRecyclerViewAdapter(private val context: Context) :
    ListAdapter<Habit, HabitRecyclerViewAdapter.HabitViewHolder>(DiffCallback) {

    lateinit var deleteItemClickListener: (habit: Habit) -> Unit
    lateinit var checkDoneItemClickListener: (habit: Habit) -> Unit
    lateinit var isHabitChecked: (habit: Habit) -> Boolean

    class HabitViewHolder(binding: HabitItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val habitDescription = binding.habitDescription
        val streakTextview = binding.streakTextview
        val deleteBtn = binding.deleteBtn
        val checkDoneBtn = binding.checkDoneBtn
        val habitDoneImg = binding.habitDone
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        return HabitViewHolder(
            HabitItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val currentHabit = getItem(position)
        holder.run {
            habitDescription.text = currentHabit.text
            streakTextview.text = currentHabit.streak.toString()

            if (isHabitChecked(currentHabit)) {
                habitDoneImg.visibility = View.VISIBLE
                checkDoneBtn.text = context.getText(R.string.done)
            } else {
                habitDoneImg.visibility = View.INVISIBLE
                checkDoneBtn.text = context.getText(R.string.check)
            }

            deleteBtn.setOnClickListener { deleteItemClickListener(currentHabit) }
            checkDoneBtn.setOnClickListener {
                checkDone(holder)
                checkDoneItemClickListener(currentHabit)
            }
        }
    }

    private fun checkDone(item: HabitViewHolder) {
        item.checkDoneBtn.text = context.getText(R.string.done)
        item.habitDoneImg.visibility = View.VISIBLE
    }
}

object DiffCallback : DiffUtil.ItemCallback<Habit>() {
    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.id == newItem.id && oldItem.text == newItem.text && oldItem.streak == newItem.streak
                && oldItem.lastUpdate == newItem.lastUpdate
    }
}
