package com.cotton.candy.todo.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.TimePicker
import com.cotton.candy.todo.databinding.FragmentTaskBinding
import java.util.*

class TaskFragment : BaseFragment<FragmentTaskBinding>()
    , DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute =0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = ""
    var savedMinute =""

    override val LOG_TAG: String
        get() = "Task_Fargment"
    override val bindingInflater: (LayoutInflater) -> FragmentTaskBinding = FragmentTaskBinding::inflate

    override fun setup() {    }

    override fun addCallBack() {
        binding!!.apply {
            datePickerButton.setOnClickListener {
                getDateTimeCalender()
                DatePickerDialog((activity)!!,this@TaskFragment, year, month, day).show()
            }
            timePickerButton.setOnClickListener{
                TimePickerDialog(activity,this@TaskFragment,hour,minute,true).show()
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
        savedMonth = month+1
        savedYear = year

        getDateTimeCalender()
        binding!!.datePickerButton.text = " ${getMonthFormat(savedMonth)} ${savedDay} ${savedYear} "
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = if(hourOfDay < 10 && hourOfDay >= 0)  "0$hourOfDay" else "$hourOfDay"
        savedMinute =  if(minute < 10 && minute >= 0)  "0$minute" else "$minute"

        binding!!.timePickerButton.text =" $savedHour : $savedMinute"

    }
    private fun getMonthFormat(month: Int) =
        when(month){
            1 ->  "JAN"
            2 ->  "FEB"
            3 ->  "MAR"
            4 ->  "APR"
            5 ->  "MAY"
            6 ->  "JUN"
            7 ->  "JUL"
            8 ->  "AUG"
            9 ->  "SEP"
            10 -> "OCT"
            11 -> "NOV"
            12 -> "DEC"
            else -> "JAN"
    }

}