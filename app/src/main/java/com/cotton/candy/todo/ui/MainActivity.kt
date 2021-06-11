package com.cotton.candy.todo.ui


import android.util.Log
import android.view.LayoutInflater
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cotton.candy.todo.R
import com.cotton.candy.todo.adapters.TaskAdapter
import com.cotton.candy.todo.data.TaskModel
import com.cotton.candy.todo.data.dataBase.TablesDetiles
import com.cotton.candy.todo.data.dataBase.TaskDataBase
import com.cotton.candy.todo.databinding.ActivityMainBinding
import com.cotton.candy.todo.ui.fragment.TaskFragment


class MainActivity : BaseActivity<ActivityMainBinding>(), NotifyAdapterNewTask {
    lateinit var moveStars: Animation
    private lateinit var dataBaseHelper: TaskDataBase
    private lateinit var taskModel: TaskModel
    private val taskItems = ArrayList<TaskModel>()


    override val LOG_TAG: String
        get() = "Main_Activity"
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    private val _TaskFragment = TaskFragment(this)

    override fun setUp() {
        dataBaseHelper = TaskDataBase(applicationContext)
        getTaskList()
    }

    private fun getTaskList() {
        val cursor = dataBaseHelper.readableDatabase.rawQuery(
            "SELECT * FROM ${TablesDetiles.TABLE_NAME}",
            arrayOf<String>()
        )
        while (cursor.moveToNext()) {
            taskModel = TaskModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
            )
            taskItems.add(taskModel)
        }
        cursor.close()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        taskItems.reverse()
        binding?.taskRecycler?.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = TaskAdapter(taskItems)
        }
    }

    override fun onAddNewTask(task: TaskModel) {
        taskItems.add(0,task)
        binding?.taskRecycler?.adapter?.notifyDataSetChanged()
    }

    override fun addCallbacks() {

        //this part to add animation in image tha content stars
//        moveStars = AnimationUtils.loadAnimation(applicationContext,R.anim.move_stars)
//        moveStars.repeatCount = Animation.INFINITE
//        binding!!.image1.startAnimation(moveStars)
//        binding!!.image2.startAnimation(moveStars)
//        binding!!.image3.startAnimation(moveStars)
//        binding!!.image4.startAnimation(moveStars)
//        binding!!.image5.startAnimation(moveStars)
//        binding!!.image6.startAnimation(moveStars)


        binding!!.fabAddTask.setOnClickListener {
            loadFragments(_TaskFragment)
        }
    }

    //load fragment to enter tasks with simple animation
    private fun loadFragments(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.apply {
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