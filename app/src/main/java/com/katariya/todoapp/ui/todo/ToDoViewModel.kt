package com.katariya.todoapp.ui.todo

import androidx.lifecycle.ViewModel
import com.katariya.todoapp.data.database.ToDoItem

class ToDoViewModel(private val repository: ToDoRepository) : ViewModel() {

    fun getToDoItems() = repository.getToDoList()
    fun getPinToDoItems() = repository.getPinToDoList()
    fun deleteAllToDoData() = repository.deleteAllToDoData()

    fun updateToDo(toDoItem: ToDoItem) {
        repository.updateToDo(toDoItem)
        getPinToDoItems()
    }

    fun deleteToDo(toDoItem: ToDoItem) {
        repository.deleteToDo(toDoItem)
        getToDoItems()
    }
}