package com.cotton.candy.todo.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.cotton.candy.todo.data.TodoItemModel
import com.cotton.candy.todo.databinding.TodoRowItemBinding
import com.cotton.candy.todo.ui.TodoCheckBoxListener

class TodoListAdapter(val todoList: ArrayList<TodoItemModel>, val checkBoxListener: TodoCheckBoxListener): RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){


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
            if (todoList[position].isCompleted == 1){
                todoTitleTextView.paintFlags =  Paint.STRIKE_THRU_TEXT_FLAG
            }
            else{
                todoTitleTextView.paintFlags = Paint.LINEAR_TEXT_FLAG
            }

            todoItemCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                val item = TodoItemModel(
                    todoList[position].id,
                    todoList[position].todoTaskId,
                    todoList[position].title,
                    if (isChecked) 1 else 0
                )
                if (isChecked){
                    todoTitleTextView.paintFlags =  Paint.STRIKE_THRU_TEXT_FLAG
                }
                else{
                    todoTitleTextView.paintFlags = Paint.LINEAR_TEXT_FLAG
                }
                checkBoxListener.onCheckChangeListener(item)
            }
        }
    }

    override fun getItemCount() = todoList.size

    class TodoViewHolder(val binding : TodoRowItemBinding?): RecyclerView.ViewHolder(binding?.root!!)
}