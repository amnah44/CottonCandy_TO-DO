package com.cotton.candy.todo.ui

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.cotton.candy.todo.R
import com.cotton.candy.todo.databinding.ActivityMainBinding
import com.cotton.candy.todo.fragment.TaskFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val LOG_TAG: String
        get() = "Main_Activity"
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate


    override fun setUp() {   }

    override fun addCallbacks() {
        binding!!.fabAddTask.setOnClickListener{
            loadFragments(TaskFragment())
        }
    }

    private fun loadFragments(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()

        }
    }
}