package com.example.shoppinglistapp

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.databinding.ItemInListBinding

class ItemViewHolder(
    private val context: Context,
    private val binding: ItemInListBinding,
    private val clickListener: ItemClickListener,

): RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: Item) {
        binding.tvItemName.text = item.name
        if (item.isBough()) {
            binding.cvItemContainer.alpha = 0.6F
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