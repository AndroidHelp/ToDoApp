package com.katariya.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katariya.todoapp.data.pref.AppPreferences

class ToDoHostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_host)
        AppPreferences.with(this.application)
    }
}