package com.example.shoppinglistapp

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("items_table")
class Item(
    @ColumnInfo("name") var name: String,
    @ColumnInfo("description") var description: String?,
    @ColumnInfo("amount") var amount: Double?,
    @ColumnInfo("bought") var bought: Boolean = false,
    @PrimaryKey(true) val id: Int = 0
) {

    fun isBough() = bought
    fun imageResource(): Int = if(isBough()) R.drawable.ic_check else R.drawable.ic_cart
    fun imageColor(context: Context): Int = if(isBough()) boughtColor(context)
    else notBoughtColor(context)

    private fun boughtColor(context: Context) = ContextCompat.getColor(context, R.color.customColor)
    private fun notBoughtColor(context: Context) =
        ContextCompat.getColor(context, androidx.appcompat.R.color.material_grey_300)
}