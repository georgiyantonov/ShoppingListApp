package com.example.shoppinglistapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.databinding.ItemInListBinding
import com.example.shoppinglistapp.entities.Item
import com.example.shoppinglistapp.ui.listeners.ItemClickListener
import com.example.shoppinglistapp.ui.viewholders.ItemViewHolder

class ItemsAdapter(
    private val items: List<Item>,
    private val clickListener: ItemClickListener,

    ): RecyclerView.Adapter<ItemViewHolder>() {

    private  val differCallback = object  : DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return  oldItem.equals(newItem)
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

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