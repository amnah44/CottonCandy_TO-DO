package com.cotton.candy.todo.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity() {

    abstract val LOG_TAG: String
    private var _binding: ViewBinding? = null



    abstract val bindingInflater: (LayoutInflater) -> VB

    protected val binding
        get() = _binding as VB?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(requireNotNull(_binding).root)


        setUp()
        addCallbacks()
    }

    abstract fun addCallbacks()

    abstract fun setUp()

    protected fun log(value: Any) {
        Log.v(LOG_TAG, value.toString())
    }

//    fun showToast(Toast:String){
//
//    }

}