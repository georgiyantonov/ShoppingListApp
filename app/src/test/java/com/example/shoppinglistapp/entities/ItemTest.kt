package com.example.shoppinglistapp.entities

import com.example.shoppinglistapp.R
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ItemTest {
    @Test
    fun `Creating instance of Item`() {
        val item = Item("test", "test", 2f)
        assertThat(item).isInstanceOf(Item::class.java)
    }
    @Test
    fun `Item description and amount are nullable`() {
        val item = Item("test", null, null)
        assertThat(item).isInstanceOf(Item::class.java)
    }
    @Test
    fun `Item isn't bought by default`() {
        val item = Item("test", null, null)
        assertThat(item.bought).isFalse()
    }
    @Test
    fun `Item isBought icon depends on bought param`() {
        val item = Item("test", null, null)
        val item2 = Item("test", null, null, true)
        assertThat(item.imageResource()).isNotEqualTo(item2.imageResource())
    }

    @Test
    fun `Item isBought icon is check`() {
        val item = Item("test", null, null, true)
        assertThat(item.imageResource()).isEqualTo(R.drawable.ic_check)
    }

    @Test
    fun `Item isBought icon color is green`() {
        val item = Item("test", null, null, true)
        assertThat(item.imageResource()).isEqualTo(R.drawable.ic_check)
    }
}

