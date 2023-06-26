package edu.karolinawidz.beconsistent.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import edu.karolinawidz.beconsistent.databinding.HabitItemBinding
import edu.karolinawidz.beconsistent.ui.model.Habit


class HabitRecyclerViewAdapter :
    ListAdapter<Habit, HabitRecyclerViewAdapter.HabitViewHolder>(DiffCallback) {

    class HabitViewHolder(private var binding: HabitItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(habit: Habit) {
            binding.apply {
                habitDescription.text = habit.text
                streakBtn.text = habit.streak.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        return HabitViewHolder(
            HabitItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val currentHabit = getItem(position)
        holder.bind(currentHabit)
    }

}

object DiffCallback : DiffUtil.ItemCallback<Habit>() {
    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.text == newItem.text
    }

}
