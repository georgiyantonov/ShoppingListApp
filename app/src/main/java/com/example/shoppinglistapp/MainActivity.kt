package com.example.shoppinglistapp

import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.databinding.ActivityMainBinding
import com.example.shoppinglistapp.listeners.ItemClickListener
import com.example.shoppinglistapp.listeners.ItemSwipe
import com.example.shoppinglistapp.listeners.ItemSwipeListener
import com.example.shoppinglistapp.notification.PushBroadcastReceiver
import com.example.shoppinglistapp.notification.PushService

class MainActivity : AppCompatActivity(), ItemClickListener, ItemSwipeListener {

    private lateinit var binding: ActivityMainBinding
    private val itemViewModel: ItemViewModel by viewModels {
        ItemModelFactory((application as ItemsApplication).repository)
    }
    private lateinit var pushBroadcastReceiver: BroadcastReceiver
    private lateinit var adapterRV: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pushBroadcastReceiver = PushBroadcastReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(PushService.INTENT_FILTER)
        registerReceiver(pushBroadcastReceiver, intentFilter)

        binding.btnNewItem.setOnClickListener {
            NewItemSheet(null).show(supportFragmentManager, "NewItemSheet")
        }
        binding.btnClear.setOnClickListener {
            itemViewModel.deleteAllItems()
        }
        setRecyclerView()
    }

    override fun onDestroy() {
        unregisterReceiver(pushBroadcastReceiver)
        super.onDestroy()
    }

    private fun setRecyclerView() {
        val mainActivity = this
        itemViewModel.items.observe(mainActivity) {
            binding.rvItemsList.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapterRV = ItemsAdapter(it, mainActivity)
                adapter = adapterRV
            }
        }
        val swipeHandler = object : ItemSwipe(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapterRV.differ.currentList[position]
                deleteItem(item)
                binding.rvItemsList.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
                Log.e("tag", "${viewHolder.adapterPosition}")
            }
        }
        itemViewModel.items.observe(mainActivity) {
            adapterRV.differ.submitList(it)
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvItemsList)
    }

    override fun editItem(item: Item) {
        NewItemSheet(item).show(supportFragmentManager, "NewItemSheet")
    }

    override fun buyItem(item: Item) {
        itemViewModel.setBought(item)
    }

    override fun deleteItem(item: Item) {
        itemViewModel.deleteItem(item)
    }
}