package com.example.shoppinglistapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.databinding.ItemInListBinding

class ItemsAdapter(
    private val items: List<Item>,
    private val clickListener: ItemClickListener,

): RecyclerView.Adapter<ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemInListBinding.inflate(from, parent, false)
        return ItemViewHolder(parent.context, binding, clickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(items[position])
    }
}