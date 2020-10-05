package com.katariya.todoapp.ui.todo.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.katariya.todoapp.ui.todo.ToDoRepository

class AddToDoViewModelFactory (private val toDoRepository: ToDoRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return if (modelClass.isAssignableFrom(AddToDoViewModel::class.java)) AddToDoViewModel(this.toDoRepository) as T
        else throw IllegalArgumentException("ViewModel not found")
    }

}