package com.example.shoppinglistapp.ui.viewholders

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.databinding.ItemInListBinding
import com.example.shoppinglistapp.entities.Item
import com.example.shoppinglistapp.ui.listeners.ItemClickListener

class ItemViewHolder(
    private val context: Context,
    private val binding: ItemInListBinding,
    private val clickListener: ItemClickListener,

    ): RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: Item) {
        binding.tvItemName.text = item.name
        if (item.isBough()) {
            binding.ibBought.visibility = View.VISIBLE
        }
        binding.ibBought.setImageResource(item.imageResource())
        binding.ibBought.setColorFilter(item.imageColor(context))
        binding.ibBought.setOnClickListener{
            clickListener.buyItem(item)

        }
        binding.cvItemContainer.setOnClickListener{
            clickListener.editItem(item)
        }

        if (item.amount !=  null) binding.tvItemAmount.text = item.amount!!.toInt().toString()
    }

}