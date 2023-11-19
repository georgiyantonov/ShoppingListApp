package com.example.shoppinglistapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistapp.databinding.ActivityMainBinding
import com.example.shoppinglistapp.notification.PushBroadcastReceiver
import com.example.shoppinglistapp.notification.PushService

class MainActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val itemViewModel: ItemViewModel by viewModels {
        ItemModelFactory((application as ItemsApplication).repository)
    }
    private lateinit var pushBroadcastReceiver: BroadcastReceiver

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
                adapter = ItemsAdapter(it, mainActivity)
            }
        }
    }

    override fun editItem(item: Item) {
        NewItemSheet(item).show(supportFragmentManager, "NewItemSheet")
    }

    override fun buyItem(item: Item) {
        itemViewModel.setBought(item)
    }
}