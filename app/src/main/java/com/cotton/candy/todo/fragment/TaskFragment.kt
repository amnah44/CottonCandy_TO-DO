package com.cotton.candy.todo.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.TimePicker
import com.cotton.candy.todo.databinding.FragmentTaskBinding
import java.util.*

class TaskFragment : BaseFragment<FragmentTaskBinding>() , DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute =0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute =0

    override val LOG_TAG: String
        get() = "Task_Fargment"
    override val bindingInflater: (LayoutInflater) -> FragmentTaskBinding
        get() = FragmentTaskBinding::inflate

    override fun setup() {    }

    override fun addCallBack() {
        binding!!.apply {
            datePickerButton.setOnClickListener {
                getDateTimeCalender()

                DatePickerDialog((activity)!!,this@TaskFragment, year, month, day).show()
            }
        }
    }

    private fun getDateTimeCalender() {
        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.DAY_OF_MONTH)
        year = cal.get(Calendar.DAY_OF_MONTH)
        hour = cal.get(Calendar.DAY_OF_MONTH)
        minute = cal.get(Calendar.DAY_OF_MONTH)

    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalender()
        TimePickerDialog(activity,this,hour,minute,true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        binding!!.textDate.text ="${savedDay} - ${savedMonth} - ${savedYear} - hour: $savedHour minute: $savedMinute"

    }



}