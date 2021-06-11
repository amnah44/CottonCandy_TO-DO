package com.cotton.candy.todo.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import com.cotton.candy.todo.R
import com.cotton.candy.todo.dataBase.TablesDetiles
import com.cotton.candy.todo.dataBase.TaskDataBase
import com.cotton.candy.todo.databinding.FragmentTaskBinding
import com.cotton.candy.todo.ui.MainActivity
import java.util.*

class TaskFragment : BaseFragment<FragmentTaskBinding>(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = ""
    var savedMinute = ""

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
                getDateTimeCalender()

                DatePickerDialog((activity)!!, this@TaskFragment,year, month, day).show()
            }


            timePickerButton.setOnClickListener {
                TimePickerDialog(activity, this@TaskFragment, hour, minute, true).show()
            }

            // add task details to database when click to button
            addTask.setOnClickListener {
                addTasksToDatabase()
                (activity)!!.supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(
                        R.anim.slide_from_right,
                        R.anim.slideout_from_left,
                        R.anim.slide_from_left,
                        R.anim.slideout_from_right
                    )
                    remove(this@TaskFragment)
                    addToBackStack(java.lang.String.valueOf(MainActivity()))
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
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

    private fun getDateTimeCalender() {
        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.DAY_OF_MONTH)
        year = cal.get(Calendar.DAY_OF_MONTH)
        hour = cal.get(Calendar.DAY_OF_MONTH)
        minute = cal.get(Calendar.DAY_OF_MONTH)

    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year

        getDateTimeCalender()
        binding!!.datePickerButton.text = " ${getMonthFormat(savedMonth)} ${savedDay} ${savedYear} "
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = if (hourOfDay in 0..9) "0$hourOfDay" else "$hourOfDay"
        savedMinute = if (minute in 0..9) "0$minute" else "$minute"

        binding!!.timePickerButton.text = " $savedHour : $savedMinute"

    }

    private fun getMonthFormat(month: Int) =
        when (month) {
            1 -> "JAN"
            2 -> "FEB"
            3 -> "MAR"
            4 -> "APR"
            5 -> "MAY"
            6 -> "JUN"
            7 -> "JUL"
            8 -> "AUG"
            9 -> "SEP"
            10 -> "OCT"
            11 -> "NOV"
            12 -> "DEC"
            else -> "JAN"
        }

}