package com.example.shoppinglistapp.repositories

import androidx.annotation.WorkerThread
import com.example.shoppinglistapp.db.ItemDao
import com.example.shoppinglistapp.entities.Item
import kotlinx.coroutines.flow.Flow

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