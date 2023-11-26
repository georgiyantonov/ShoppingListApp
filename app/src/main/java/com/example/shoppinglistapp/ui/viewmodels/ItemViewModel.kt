package com.example.shoppinglistapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistapp.entities.Item
import com.example.shoppinglistapp.repositories.ItemsRepository
import kotlinx.coroutines.launch

class ItemViewModel(
    private val repository: ItemsRepository
): ViewModel() {

    var items: LiveData<List<Item>> = repository.allItems.asLiveData()

    fun addItem(newItem: Item) = viewModelScope.launch {
        repository.insertItem(newItem)
    }

    fun updateItem(item: Item) = viewModelScope.launch {
        repository.updateItem(item)
    }

    fun deleteItem(item: Item) = viewModelScope.launch {
        repository.deleteItem(item)
    }

    fun deleteAllItems() = viewModelScope.launch {
        repository.deleteAllItems()
    }

    fun setBought(item: Item)  = viewModelScope.launch {
        item.bought = !item.bought
        repository.updateItem(item)
    }
}

class ItemModelFactory(
    private val repository: ItemsRepository
): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ItemViewModel::class.java))
            return ItemViewModel(repository) as T
        throw IllegalArgumentException("Unknown class for View")
    }

}