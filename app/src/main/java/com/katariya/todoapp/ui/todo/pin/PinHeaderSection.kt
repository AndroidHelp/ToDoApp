package com.katariya.todoapp.ui.todo.pin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.katariya.todoapp.R

class PinHeaderSection: RecyclerView.Adapter<PinHeaderSection.HeaderViewHolder>() {

    var sectionSize = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.header_pinned_section, parent, false))
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = sectionSize

    inner class HeaderViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }
}
