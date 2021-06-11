package com.cotton.candy.todo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cotton.candy.todo.data.TaskModel
import com.cotton.candy.todo.databinding.RowItemBinding
import com.cotton.candy.todo.ui.TaskItemClickListener

class TaskAdapter(val taskList: ArrayList<TaskModel>, val listener: TaskItemClickListener) :RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: RowItemBinding = RowItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding?.apply {
            taskTextView.text = taskList[position].task
            noteTextView.text = taskList[position].note
            timeTextView.text = taskList[position].time
            dateTextView.text = taskList[position].date
        }
        holder.binding?.root?.setOnClickListener{
            listener.onTaskItemClick(taskList[position])
        }
    }

    override fun getItemCount() = taskList.size

    class TaskViewHolder (val binding : RowItemBinding?): RecyclerView.ViewHolder(binding?.root!!)
}