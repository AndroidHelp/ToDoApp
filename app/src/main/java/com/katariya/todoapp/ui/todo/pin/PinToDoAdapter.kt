package com.katariya.todoapp.ui.todo.pin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.katariya.todoapp.R
import com.katariya.todoapp.data.database.ToDoItem
import kotlinx.android.synthetic.main.fragment_pin_todo_item.view.*
import kotlinx.android.synthetic.main.fragment_todo_item.view.tvDescription
import kotlinx.android.synthetic.main.fragment_todo_item.view.tvTitle

class PinToDoAdapter(
    private val context: Context,
    private val todoList: List<ToDoItem>,
    private val itemClickListener: PinToDoItemClickListener
) : RecyclerView.Adapter<PinToDoAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_pin_todo_item, parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        //   holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.anim_fall_item)
        val toDoItem = todoList[position]
        holder.bindItem(toDoItem)
        holder.itemView.ivDelete.setOnClickListener {
            toDoItem.pin = false
            itemClickListener.onPinItemDelete(toDoItem)
        }
    }

    override fun getItemCount(): Int = todoList.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(toDoItem: ToDoItem) {
            itemView.apply {
                tvTitle.text = toDoItem.title
                tvDescription.text = toDoItem.description
            }
        }
    }
}

interface PinToDoItemClickListener {
    fun onPinItemDelete(toDoItem: ToDoItem)
}