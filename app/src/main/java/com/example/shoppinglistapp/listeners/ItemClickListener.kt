package com.example.shoppinglistapp.listeners

import com.example.shoppinglistapp.Item

interface ItemClickListener {
    fun editItem(item: Item)
    fun buyItem(item: Item)
}