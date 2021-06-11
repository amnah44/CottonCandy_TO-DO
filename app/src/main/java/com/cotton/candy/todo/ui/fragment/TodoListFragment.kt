package com.cotton.candy.todo.ui.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cotton.candy.todo.adapters.TaskAdapter
import com.cotton.candy.todo.adapters.TodoListAdapter
import com.cotton.candy.todo.data.TaskModel
import com.cotton.candy.todo.data.TodoItemModel
import com.cotton.candy.todo.data.dataBase.TablesDetiles
import com.cotton.candy.todo.data.dataBase.TaskDataBase
import com.cotton.candy.todo.databinding.FragmentTodoBinding
import com.cotton.candy.todo.ui.TodoCheckBoxListener
import com.cotton.candy.todo.util.Constants
import com.google.android.material.tabs.TabLayout

class TodoListFragment : BaseFragment<FragmentTodoBinding>(), TodoCheckBoxListener {
    override val LOG_TAG: String
        get() = "TODO_FRAGMENT"
    override val bindingInflater: (LayoutInflater) -> FragmentTodoBinding =
        FragmentTodoBinding::inflate
    private var taskID: Int = 0
    lateinit var dataBaseHelper: TaskDataBase
    private lateinit var todoItem: TodoItemModel
    private val todoListItems = ArrayList<TodoItemModel>()

    private fun getArgs(arg: Bundle?) =
        arg?.let {
            taskID = it.getInt(Constants.KEY_ID)
            Log.i("argCity", taskID.toString())

        }

    override fun setup() {
        super.onStart()
        try {
            getArgs(arguments)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        dataBaseHelper = TaskDataBase(requireActivity())
        getTodoList()
    }

    private fun getTodoList() {
        val cursor = dataBaseHelper.readableDatabase.rawQuery(
            "SELECT * FROM ${TablesDetiles.TODO_TABLE_NAME} WHERE ${TablesDetiles.TODO_TASK_ID} = ?",
            arrayOf<String>("$taskID")
        )
        while (cursor.moveToNext()) {
            todoListItems.add(
                TodoItemModel(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3)
                )
            )
        }
        cursor.close()
        initTodoRecyclerView()
    }

    private fun initTodoRecyclerView() {
        todoListItems.reverse()
        binding?.todoRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = TodoListAdapter(todoListItems, this@TodoListFragment)
        }
    }

    override fun addCallBack() {
        binding?.addBtn?.setOnClickListener {
            val todoTitle = binding?.todoEditText?.text.toString().trim()

            val newEntry = ContentValues().apply {
                put(TablesDetiles.TODO_TASK_ID, taskID)
                put(TablesDetiles.TODO_TITLE, todoTitle)
                put(TablesDetiles.TODO_IS_COMPLETED, 0)
            }
            val id = dataBaseHelper.writableDatabase.insert(
                TablesDetiles.TODO_TABLE_NAME,
                null,
                newEntry
            )


            todoItem =
                TodoItemModel(id.toInt(), taskID, binding?.todoEditText?.text.toString().trim(), 0)
            todoListItems.add(0, todoItem)
            binding?.todoRecyclerView?.adapter?.notifyDataSetChanged()
        }
    }

    override fun onCheckChangeListener(todoItem: TodoItemModel) {
        val newEntry = ContentValues().apply {
            put(TablesDetiles.TODO_TASK_ID, todoItem.todoTaskId)
            put(TablesDetiles.TODO_TITLE, todoItem.title)
            put(TablesDetiles.TODO_IS_COMPLETED, todoItem.isCompleted)
        }
        dataBaseHelper.writableDatabase.update(
            TablesDetiles.TODO_TABLE_NAME,
            newEntry,
            "${TablesDetiles.ID} = ?",
            arrayOf<String>("${todoItem.id}")
        )
    }

}