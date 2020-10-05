package com.katariya.todoapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToDoItem(toDoItem: ToDoItem)

    @Update
    fun updateToDoItem(toDoItem: ToDoItem)

    @Delete
    fun deleteToDoItem(toDoItem: ToDoItem)

    @Query("SELECT * from todo_table ORDER BY id ASC")
    fun getToDoItems(): LiveData<List<ToDoItem>>

    @Query("SELECT * from todo_table where pin=1 ORDER BY id ASC")
    fun getPinToDoItems(): LiveData<List<ToDoItem>>

    @Query("DELETE  from todo_table")
    fun deleteAllToDoData()
}