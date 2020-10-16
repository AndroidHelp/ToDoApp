package com.katariya.todoapp.ui.todo.add

import androidx.lifecycle.ViewModel
import com.katariya.todoapp.R
import com.katariya.todoapp.data.database.ToDoItem
import com.katariya.todoapp.ui.todo.ToDoRepository

class AddToDoViewModel(private  val repository: ToDoRepository) : ViewModel() {

    var addToDoView: AddToDoView? = null

    fun validateUpdateInsert(title:String, description:String, itemId:Int){
        when {
            title.isEmpty() -> addToDoView?.validateTitle(R.string.warning_title)
            description.isEmpty() -> addToDoView?.validateDescription(R.string.warning_description)
            else -> {
                if (itemId>0) updateToDo(ToDoItem(id = itemId, title = title, description = description))
                else insertToDo(ToDoItem(title = title, description = description))
            }
        }
    }
    private fun insertToDo(toDoItem: ToDoItem) {
        repository.insertToDo(toDoItem)
    }
    private fun updateToDo(toDoItem: ToDoItem) {
        repository.updateToDo(toDoItem)
    }
}