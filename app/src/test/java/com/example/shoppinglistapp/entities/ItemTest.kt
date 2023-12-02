package com.example.shoppinglistapp.entities

import com.example.shoppinglistapp.R
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ItemTest {

    // Creating Item instance
    // Item description and amount fields are nullable
    // Bought field is false by default
    // Creating item instance with bought=true
    // isBough fun returns bought field value
    // Item icon resource is ic_cart when bought=false
    // Item icon resource is ic_check when bought=true

    @Test
    fun `Creating instance of Item`() {
        val item = Item("test", "test", 2f)
        assertThat(item).isInstanceOf(Item::class.java)
    }

    @Test
    fun `Item description and amount fields are nullable`() {
        val item = Item("test", null, null)
        assertThat(item).isInstanceOf(Item::class.java)
    }

    @Test
    fun `Bought field is false by default`() {
        val item = Item("test", null, null)
        assertThat(item.bought).isFalse()
    }

    @Test
    fun `Creating item instance with bought=true`(){
        val item = Item("test", null, null, true)
        assertThat(item.bought).isTrue()
    }

    @Test
    fun `isBough fun returns bought field value`(){
        val item = Item("test", null, null)
        assertThat(item.isBough()).isFalse()
    }

    @Test
    fun `Item icon resource is ic_cart when bought=false`() {
        val item = Item("test", null, null)
        assertThat(item.imageResource()).isEqualTo(R.drawable.ic_cart)
    }

    @Test
    fun `Item icon resource is ic_check when bought=true`() {
        val item = Item("test", null, null, true)
        assertThat(item.imageResource()).isEqualTo(R.drawable.ic_check)
    }
}

