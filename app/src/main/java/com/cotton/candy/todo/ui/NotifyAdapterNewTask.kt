package com.cotton.candy.todo.ui

import com.cotton.candy.todo.data.TaskModel

interface NotifyAdapterNewTask {
   fun onAddNewTask(task: TaskModel)
}