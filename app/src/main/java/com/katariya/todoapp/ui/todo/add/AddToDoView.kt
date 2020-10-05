package com.katariya.todoapp.ui.todo.add

interface AddToDoView {
    fun validateTitle(message:Int)
    fun validateDescription(message:Int)
}