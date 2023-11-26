package com.example.shoppinglistapp.ui.listeners

import com.example.shoppinglistapp.entities.Item

interface ItemSwipeListener {
    fun deleteItem(item: Item)
}