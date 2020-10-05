package com.katariya.todoapp.ui.todo

import android.app.Application
import com.katariya.todoapp.data.database.ToDoDao
import com.katariya.todoapp.data.database.ToDoDatabase
import com.katariya.todoapp.data.database.ToDoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ToDoRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var toDoDao: ToDoDao?

    init {
        val db = ToDoDatabase.getDatabase(application)
        toDoDao = db?.todoDao()
    }

    fun getToDoList() = toDoDao?.getToDoItems()

    fun getPinToDoList() = toDoDao?.getPinToDoItems()

    fun insertToDo(toDoItem: ToDoItem) {
        launch {
            withContext(Dispatchers.IO) {
                toDoDao?.insertToDoItem(toDoItem)
            }
        }
    }
    fun updateToDo(toDoItem: ToDoItem) {
        launch {
            withContext(Dispatchers.IO) {
                toDoDao?.updateToDoItem(toDoItem)
            }
        }
    }

    fun deleteToDo(toDoItem: ToDoItem) {
        launch {
            withContext(Dispatchers.IO) {
                toDoDao?.deleteToDoItem(toDoItem)
            }
        }
    }
}