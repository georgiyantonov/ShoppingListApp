package com.example.shoppinglistapp

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ItemsRepository(
    private val itemDao: ItemDao,
) {
    val allItems: Flow<List<Item>> = itemDao.allItems()

    @WorkerThread
    suspend fun insertItem(item: Item) {
        itemDao.insertNewItem(item)
    }

    @WorkerThread
    suspend fun updateItem(item: Item) {
        itemDao.updateItem(item)
    }

    @WorkerThread
    suspend fun deleteItem(item: Item) {
        itemDao.deleteItem(item)
    }

    @WorkerThread
    suspend fun deleteAllItems() {
        itemDao.deleteAll()
    }
}