package com.cotton.candy.todo.ui

import com.cotton.candy.todo.data.TaskModel

interface TaskItemClickListener {
    fun onTaskItemClick(task: TaskModel)
}