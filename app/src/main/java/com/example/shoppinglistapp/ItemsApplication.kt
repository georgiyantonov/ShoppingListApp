package com.example.shoppinglistapp

import android.app.Application
import android.util.Log
import com.example.shoppinglistapp.db.ItemDatabase
import com.example.shoppinglistapp.repositories.ItemsRepository
import com.google.firebase.Firebase
import com.google.firebase.installations.installations
import com.google.firebase.messaging.FirebaseMessaging

class ItemsApplication: Application() {

    private val database by lazy { ItemDatabase.getDatabase(this) }
    val repository by lazy { ItemsRepository(database.itemDao()) }

    override fun onCreate() {
        super.onCreate()

        FirebaseMessaging.getInstance().token.addOnCompleteListener {task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }

            val token = task.result
            Log.e("TAG", "Token -> $token")
        }
        Firebase.installations.getId().addOnSuccessListener {
            val id = it
            Log.e("TAG", "Instalation -> $id")
        }
    }

}