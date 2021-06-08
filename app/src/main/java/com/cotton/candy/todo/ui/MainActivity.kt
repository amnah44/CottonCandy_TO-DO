package com.cotton.candy.todo.ui

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.cotton.candy.todo.R
import com.cotton.candy.todo.databinding.ActivityMainBinding
import com.cotton.candy.todo.fragment.TaskFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val LOG_TAG: String
        get() = "Main_Activity"
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    private val _TaskFragment = TaskFragment()

    override fun setUp() {   }

    override fun addCallbacks() {
        binding!!.fabAddTask.setOnClickListener{
            loadFragments(_TaskFragment)
        }
    }

    private fun loadFragments(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, fragment)
            commit()
        }
    }
}