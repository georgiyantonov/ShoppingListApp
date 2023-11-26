package com.example.shoppinglistapp.ui.listeners

import com.example.shoppinglistapp.entities.Item

interface ItemClickListener {
    fun editItem(item: Item)
    fun buyItem(item: Item)
}