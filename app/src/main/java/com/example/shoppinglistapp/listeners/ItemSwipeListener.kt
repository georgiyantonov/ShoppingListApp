package com.example.shoppinglistapp.listeners

import com.example.shoppinglistapp.Item

interface ItemSwipeListener {
    fun deleteItem(item: Item)
}