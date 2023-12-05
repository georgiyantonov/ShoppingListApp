package com.example.shoppinglistapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.shoppinglistapp.entities.Item
import com.example.shoppinglistapp.repositories.ItemsRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ItemDaoTest {

    // getAll() - returns all items in db
    // insertNewItem() - inserts new item into db
    // updateItem() - updates existing item
    // deleteItem() - deletes item from db
    // deleteAll() - deletes all items from db

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var db: ItemDatabase
    private lateinit var dao: ItemDao
    private lateinit var repository: ItemsRepository

    @Before
    fun setup(){
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ItemDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.itemDao()
        repository = ItemsRepository(dao)
    }

    @After
    fun teardown() {
        db.close()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllItems() = runTest(UnconfinedTestDispatcher()) {
        val item = Item("test first", "first test", 1f,false)
        val item2 = Item("test second", null, 2f,true)
        val item3 = Item("test third", null, null,false)
        val itemsArray = arrayOf(item, item2, item3)
        itemsArray.forEach {
            launch {
                repository.insertItem(it)
            }
        }

        val dbList = repository.allItems.first().toTypedArray()
        assertThat(dbList.contentEquals(itemsArray)).isTrue()
    }

    @Test
    fun insertNewItem() = runTest {
        val item = Item(
              "test",
              "i'm dumb, needed to override equals method for Item class",
              null,
              false,
            )
        repository.insertItem(item)
        val savedItemArray = repository.allItems.first()[0]
        assertThat(savedItemArray == item).isTrue()
    }

    @Test
    fun updateItem() = runTest {
        val item = Item(
                "test",
                "test",
                null,
                false,
                id=1
            )
        repository.insertItem(item)
        val insertedItem = repository.allItems.first()[0]
        assertThat(insertedItem == item).isTrue()

        val updatedItem = Item(
                "testUpdate",
                "testUpdate",
                1f,
                true,
                id=1
            )
        repository.updateItem(updatedItem)
        val insertedAndUpdatedItem = repository.allItems.first()

        assertThat(insertedAndUpdatedItem[0] == updatedItem).isTrue()
        assertThat(insertedAndUpdatedItem).doesNotContain(item)
    }

    @Test
    fun deleteItem() = runTest {
        val item = Item("test", "test", 1.5f, true, id=1)
        repository.insertItem(item)
        repository.deleteItem(item)
        val deletedList = repository.allItems.first()

        assertThat(deletedList).doesNotContain(item)
    }

    @Test
    fun deleteAllItems() = runTest {
        val item = Item("test", "test", 1f, true, id=1)
        val item2 = Item("test2", "test2",  null, false, id=2)
        val item3 = Item("test3", null, 0.5f, true, id=3)
        val itemsArray = arrayOf(item, item2, item3)
        itemsArray.forEach {
            repository.insertItem(it)
        }

        repository.deleteAllItems()
        val dbList = repository.allItems.first().toTypedArray()
        assertThat(dbList.contentEquals(itemsArray)).isFalse()
    }
}