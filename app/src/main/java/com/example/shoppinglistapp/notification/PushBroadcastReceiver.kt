package com.example.shoppinglistapp.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class PushBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val extras = intent?.extras
        extras?.keySet()?.firstOrNull{ it == PushService.KEY_ACTION}?.let { key ->
            when(extras.getString(key)) {
                PushService.ACTION_SHOW_MESSAGE -> {
                    extras.getString(PushService.KEY_MESSAGE)?.let {message ->
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
                else -> Log.e("TAG", "Key not found")
            }
        }
    }

}