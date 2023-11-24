package com.example.shoppinglistapp

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.databinding.ActivityMainBinding
import com.example.shoppinglistapp.listeners.ItemClickListener
import com.example.shoppinglistapp.listeners.ItemSwipe
import com.example.shoppinglistapp.listeners.ItemSwipeListener
import com.example.shoppinglistapp.notification.PushBroadcastReceiver
import com.example.shoppinglistapp.notification.PushService
import com.google.android.material.snackbar.Snackbar

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

    override fun onResume() {
        super.onResume()
        binding.btnToWallet.setOnClickListener{
            goToWallet()
        }
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
                Log.e("Direction", "$direction")
                val position = viewHolder.adapterPosition
                val item = adapterRV.differ.currentList[position]
                if(direction == 4){
                    deleteItem(item)
                    binding.rvItemsList.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
                    Snackbar.make(binding.root, getString(R.string.snack_text),
                        Snackbar.LENGTH_SHORT).apply {
                            setAction(getString(R.string.snack_undo)) {
                                itemViewModel.addItem(item)
                            }
                        this.setBackgroundTint(ContextCompat
                            .getColor(this@MainActivity, R.color.customColor))
                        this.setTextColor(ContextCompat
                            .getColor(this@MainActivity, R.color.white))
                        this.setActionTextColor(ContextCompat
                            .getColor(this@MainActivity, R.color.white))
                        show()
                    }
                } else if(direction == 8){
                    buyItem(item)
                    binding.rvItemsList.adapter!!.notifyItemChanged(viewHolder.adapterPosition)
                }
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

    private fun goToWallet() {
        try {
            val intent = Intent()
            intent.setPackage("ru.cardsmobile.mw3")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e("ANFE", "Wallet activity not found")
            startActivity(Intent(Intent.ACTION_VIEW).setData(
                Uri.parse("market://details?id=ru.cardsmobile.mw3"))
            )
        }
    }
}