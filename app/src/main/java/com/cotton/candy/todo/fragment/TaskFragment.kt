package com.cotton.candy.todo.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.FragmentTransaction
import com.cotton.candy.todo.R
import com.cotton.candy.todo.dataBase.TablesDetiles
import com.cotton.candy.todo.dataBase.TaskDataBase
import com.cotton.candy.todo.databinding.FragmentTaskBinding
import com.cotton.candy.todo.ui.MainActivity
import java.text.DateFormat
import java.util.*

class TaskFragment : BaseFragment<FragmentTaskBinding>(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {


    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    val calendar = Calendar.getInstance()

    override val LOG_TAG: String
        get() = "Task_Fargment"

    //make object from TaskDbHelper class
    lateinit var dataBaseHelper: TaskDataBase

    override val bindingInflater: (LayoutInflater) -> FragmentTaskBinding =
        FragmentTaskBinding::inflate

    override fun setup() {
        /*
        *define the object of TaskDataBase
         */
        dataBaseHelper = TaskDataBase(requireActivity())
    }

    override fun addCallBack() {

        binding!!.apply {

            datePickerButton.setOnClickListener {
                    DatePickerDialog((activity)!! ,
                        R.style.timeAndDatePicker ,
                        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                            @SuppressLint("SetTextI18n")
                                savedDay = dayOfMonth
                                savedMonth = month + 1
                                savedYear = year
                                var dateFormat = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
                                binding!!.datePickerButton.text = " $dateFormat "
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_WEEK)
                    ).show()
            }
            timePickerButton.setOnClickListener {
                TimePickerDialog(activity, R.style.timeAndDatePicker ,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        savedHour = hourOfDay
                        savedMinute = minute
                        var timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.time)
                        binding!!.timePickerButton.text = " $timeFormat "
                    } ,
                    calendar.get(Calendar.HOUR),
                    calendar.get(Calendar.MINUTE),
                    false
                ).show()
            }

            // add task details to database when click to button
            addTask.setOnClickListener {
                addTasksToDatabase()
                (activity)!!.supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(
                        R.anim.slide_from_left,
                        R.anim.slideout_from_right
                    )
                    remove(this@TaskFragment)
                    addToBackStack(java.lang.String.valueOf(MainActivity()))
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                    commit()
               }
            }
        }
    }


    /*
    *this function is make add tasks to database
    * the first one make a variable to get values from views
    * then make new entry, and insert this entry to task table
     */

    private fun addTasksToDatabase() {

        // make a variable to save the value from view in it
        binding?.apply {
            val task = editTextTask.text.toString()
            val note = editTextNote.text.toString()
            val date = datePickerButton.text.toString()
            val time = timePickerButton.text.toString()

            // make entry to insert the value from view to database
            val newEntry = ContentValues().apply {
                put(TablesDetiles.DATE, date)
                put(TablesDetiles.NOTE, note)
                put(TablesDetiles.TASK, task)
                put(TablesDetiles.TIME, time)

            }
            //set new entry in data base
            writeInDatabase(newEntry, TablesDetiles.TABLE_NAME)

        }

    }

    /*
    * this function to insert any new entry to any table exists in tasks database
    */
    private fun writeInDatabase(newEntry: ContentValues, table_name: String) {
        dataBaseHelper.writableDatabase.insert(
            table_name,
            null,
            newEntry
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {   }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {  }


}