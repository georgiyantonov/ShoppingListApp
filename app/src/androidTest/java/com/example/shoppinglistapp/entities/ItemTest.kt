package com.example.shoppinglistapp.entities

import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.example.shoppinglistapp.R
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ItemTest{
    private val context = ApplicationProvider.getApplicationContext<android.content.Context>()
    @Test
    fun checkBoughtIconColor(){
        val item = Item("test", "test", null, true)
        assertThat(item.imageColor(context)).isEqualTo(ContextCompat.getColor(context,
            R.color.customColor))
    }
    @Test
    fun checkNotBoughtIconColor(){
        val item = Item("test", "test", null)
        assertThat(item.imageColor(context)).isEqualTo(ContextCompat.getColor(context,
            androidx.appcompat.R.color.material_grey_300))
    }
}