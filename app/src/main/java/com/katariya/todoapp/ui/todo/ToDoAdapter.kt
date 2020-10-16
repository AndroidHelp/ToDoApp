package com.katariya.todoapp.ui.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.katariya.todoapp.R
import com.katariya.todoapp.data.database.ToDoItem
import kotlinx.android.synthetic.main.fragment_todo_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class ToDoAdapter(
    private val context: Context,
    private val todoList: List<ToDoItem>,
    private val itemClickListener: ToDoItemClickListener
) : RecyclerView.Adapter<ToDoAdapter.ItemViewHolder>(), Filterable {
    private var todoListFilter: List<ToDoItem> = todoList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_todo_item, parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        //   holder.itemView.animation = AnimationUtils.loadAnimation(context, R.anim.anim_fall_item)
        val toDoItem = todoListFilter[position]
        holder.bindItem(toDoItem)
        holder.itemView.ivOptions.setOnClickListener {
            val p = PopupMenu(context, it)
            p.menuInflater.inflate(R.menu.menu_to_do_action, p.menu)
            p.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
                PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    if (item.itemId == R.id.action_menu_pin) {
                        toDoItem.pin = true
                        itemClickListener.onItemPin(toDoItem)
                    }
                    if (item.itemId == R.id.action_menu_update) {
                        itemClickListener.updateItem(toDoItem)
                    }
                    if (item.itemId == R.id.action_menu_delete) {
                        itemClickListener.onItemDelete(toDoItem)
                    }
                    return true
                }
            })
            p.show()
        }
    }

    override fun getItemCount(): Int = todoListFilter.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(toDoItem: ToDoItem) {
            itemView.apply {
                tvTitle.text = toDoItem.title
                tvDescription.text = toDoItem.description
                println("todo status ${toDoItem.id}")
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                todoListFilter = if (charSearch.isEmpty()) {
                    todoList
                } else {
                    val resultList = ArrayList<ToDoItem>()
                    for (row in todoList) {
                        if (row.title.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) or row.description.toLowerCase(
                                Locale.ROOT
                            ).contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = todoListFilter
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                todoListFilter = results?.values as ArrayList<ToDoItem>
                notifyDataSetChanged()
            }

        }
    }
}

interface ToDoItemClickListener {
    fun onItemDelete(toDoItem: ToDoItem)
    fun updateItem(toDoItem: ToDoItem)
    fun onItemPin(toDoItem: ToDoItem)
}