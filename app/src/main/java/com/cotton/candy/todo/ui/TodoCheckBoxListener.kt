package com.cotton.candy.todo.ui

import com.cotton.candy.todo.data.TodoItemModel

interface TodoCheckBoxListener{
    fun onCheckChangeListener(todoItem: TodoItemModel)
}