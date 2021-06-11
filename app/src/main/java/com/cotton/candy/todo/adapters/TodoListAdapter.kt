package com.cotton.candy.todo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cotton.candy.todo.data.TodoItemModel
import com.cotton.candy.todo.databinding.TodoRowItemBinding

class TodoListAdapter(val todoList: ArrayList<TodoItemModel>): RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view: TodoRowItemBinding = TodoRowItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding?.apply {
            todoTitleTextView.text = todoList[position].title
            todoItemCheckBox.isChecked = todoList[position].isCompleted==1
        }
    }

    override fun getItemCount() = todoList.size

    class TodoViewHolder(val binding : TodoRowItemBinding?): RecyclerView.ViewHolder(binding?.root!!)
}