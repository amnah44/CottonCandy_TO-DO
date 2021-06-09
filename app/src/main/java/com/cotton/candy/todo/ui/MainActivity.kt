package com.cotton.candy.todo.ui

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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

    //load fragment to enter tasks with simple animation
    private fun loadFragments(fragment: Fragment){
        val transaction =  supportFragmentManager.beginTransaction()
        transaction?.apply {
            setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slideout_from_left,
                R.anim.slide_from_left,
                R.anim.slideout_from_right
            )
            add(R.id.fragment_container, fragment)
            addToBackStack(java.lang.String.valueOf(this))
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            commit()
        }
    }
}