package com.example.latihan1.adapter

import android.annotation.SuppressLint
import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.latihan1.databinding.ItemTodoBinding
import com.example.latihan1.entity.Todo

class Todoadapter (
    private val dataset: MutableList<Todo>
) : RecyclerView.Adapter<Todoadapter.CustomViewHolder>() {
    inner class CustomViewHolder(val view: ItemTodoBinding)
        : RecyclerView.ViewHolder(view.root) {

            fun bindData(item: Todo) {
                view.title.text = item.title
                view.description.text = item.description
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int
    ): CustomViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CustomViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder,
                                  position: Int
    ) {
        val data = dataset[position]
        holder.bindData(data)

    }

    @SuppressLint("NotifyDataSetChanges")
    fun updateDataset(data: List<Todo>) {
        dataset.clear()
        dataset.addAll(data)
        notifyDataSetChanged()
    }

}
