package com.katariya.todoapp.ui.todo.add

import androidx.lifecycle.ViewModel
import com.katariya.todoapp.R
import com.katariya.todoapp.data.database.ToDoItem
import com.katariya.todoapp.ui.todo.ToDoRepository

class AddToDoViewModel(private  val repository: ToDoRepository) : ViewModel() {

    var addToDoView: AddToDoView? = null

    fun validateInsert(title:String, description:String){
        when {
            title.isEmpty() -> addToDoView?.validateTitle(R.string.warning_title)
            description.isEmpty() -> addToDoView?.validateDescription(R.string.warning_description)
            else -> insertToDo(ToDoItem(title = title, description = description))
        }
    }
    private fun insertToDo(toDoItem: ToDoItem) {
        repository.insertToDo(toDoItem)
    }
}