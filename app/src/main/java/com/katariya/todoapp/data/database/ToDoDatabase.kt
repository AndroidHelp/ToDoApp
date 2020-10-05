package com.katariya.todoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDoItem::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase(){
    abstract fun todoDao():ToDoDao

    companion object{
        @Volatile
        private var INSTANCE:ToDoDatabase? = null

        fun getDatabase(context: Context):ToDoDatabase?{
            if (INSTANCE == null){
                synchronized(ToDoDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, ToDoDatabase::class.java, "todo_database").build()
                }
            }
            return INSTANCE
        }
    }
}