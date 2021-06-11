package com.cotton.candy.todo.dataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TaskDataBase(context: Context) : SQLiteOpenHelper(context, DBNAME, null, DBVERSION) {

    /*
     *on create database
    */
    override fun onCreate(dataBase: SQLiteDatabase?) {
        val sql = "CREATE TABLE ${TablesDetiles.TABLE_NAME}(" +
                "${TablesDetiles.ID} INTEGER PRIMARY KEY," +
                "${TablesDetiles.NOTE} TEXT," +
                "${TablesDetiles.DATE} TEXT," +
                "${TablesDetiles.TIME} TEXT," +
                "${TablesDetiles.TASK} TEXT" +
                ")"
        dataBase?.execSQL(sql)
    }

    /*
      *on update version
    */
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    companion object {
        private const val DBNAME = "TaskDbHelper"
        private const val DBVERSION = 1
    }
}