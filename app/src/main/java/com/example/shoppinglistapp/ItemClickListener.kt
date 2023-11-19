package com.example.shoppinglistapp

interface ItemClickListener {
    fun editItem(item: Item)
    fun buyItem(item: Item)
}