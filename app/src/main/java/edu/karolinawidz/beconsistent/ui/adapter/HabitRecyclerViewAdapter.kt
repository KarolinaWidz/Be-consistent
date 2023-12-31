package edu.karolinawidz.beconsistent.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.karolinawidz.beconsistent.R
import edu.karolinawidz.beconsistent.data.model.Habit
import edu.karolinawidz.beconsistent.databinding.HabitItemBinding


class HabitRecyclerViewAdapter(private val context: Context) :
    ListAdapter<Habit, HabitRecyclerViewAdapter.HabitViewHolder>(DiffCallback) {

    lateinit var checkDoneItemClickListener: (habit: Habit) -> Unit
    lateinit var isHabitChecked: (habit: Habit) -> Boolean
    lateinit var clearBrokenStreak: (habit: Habit) -> Unit
    lateinit var detailsItemClickListener:(habit:Habit) -> Unit

    inner class HabitViewHolder(binding: HabitItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val habitDescription = binding.habitDescription
        val streakTextview = binding.streakTextview
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
        clearBrokenStreak(currentHabit)
        holder.run {
            itemView.setOnClickListener{detailsItemClickListener(currentHabit)}
            habitDescription.text = currentHabit.text
            streakTextview.text = currentHabit.streak.toString()

            checkDoneBtn.setOnClickListener {
                checkDone(holder)
                checkDoneItemClickListener(currentHabit)
            }

            if (isHabitChecked(currentHabit)) {
                checkDone(holder)
            } else {
                undoneHabit(holder)
            }
        }
    }

    private fun checkDone(item: HabitViewHolder) {
        item.checkDoneBtn.text = context.getText(R.string.done)
        item.habitDoneImg.visibility = View.VISIBLE
        item.checkDoneBtn.isClickable = false
    }

    private fun undoneHabit(item: HabitViewHolder) {
        item.checkDoneBtn.text = context.getText(R.string.check)
        item.habitDoneImg.visibility = View.INVISIBLE
        item.checkDoneBtn.isClickable = true
    }
}

object DiffCallback : DiffUtil.ItemCallback<Habit>() {
    override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
        return oldItem == newItem
    }
}
