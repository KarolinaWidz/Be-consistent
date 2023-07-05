package edu.karolinawidz.beconsistent.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import edu.karolinawidz.beconsistent.database.Habit
import edu.karolinawidz.beconsistent.databinding.HabitItemBinding


class HabitRecyclerViewAdapter :
    ListAdapter<Habit, HabitRecyclerViewAdapter.HabitViewHolder>(DiffCallback) {

    lateinit var deleteItemClickListener: (habit: Habit) -> Unit
    lateinit var checkDoneItemClickListener: (habit: Habit) -> Unit

    class HabitViewHolder(binding: HabitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val habitDescription = binding.habitDescription
        val streakTextview = binding.streakTextview
        val deleteBtn = binding.deleteBtn
        val checkDoneBtn = binding.checkDoneBtn
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
            deleteBtn.setOnClickListener { deleteItemClickListener(currentHabit) }
            checkDoneBtn.setOnClickListener {
                checkDoneItemClickListener(currentHabit)
            }
        }
    }
}

object DiffCallback : DiffUtil.ItemCallback<Habit>() {
    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.text == newItem.text && oldItem.streak == newItem.streak
    }
}
