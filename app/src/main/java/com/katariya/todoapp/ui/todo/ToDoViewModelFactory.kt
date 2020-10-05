package com.katariya.todoapp.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ToDoViewModelFactory (private val toDoRepository: ToDoRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       return if (modelClass.isAssignableFrom(ToDoViewModel::class.java)) ToDoViewModel(this.toDoRepository) as T
        else throw IllegalArgumentException("ViewModel not found")
    }

}