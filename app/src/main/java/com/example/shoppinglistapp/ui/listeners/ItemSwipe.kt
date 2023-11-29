package com.example.shoppinglistapp.ui.listeners

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.R

abstract class ItemSwipe(context: Context): ItemTouchHelper.SimpleCallback(0,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete)
    private val delIntrinsicWidth = deleteIcon!!.intrinsicWidth
    private val delIntrinsicHeight = deleteIcon!!.intrinsicHeight

    private val buyIcon = ContextCompat.getDrawable(context, R.drawable.ic_cart)
    private val buyIntrinsicWidth = buyIcon!!.intrinsicWidth
    private val buyIntrinsicHeight = buyIcon!!.intrinsicHeight

    private lateinit var icon: Drawable
    private val background = ColorDrawable()
    private val deleteBackgroundColor = Color.parseColor("#f44336")
    private val buyBackgroundColor = ContextCompat.getColor(context, R.color.customColor)
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive
        itemView.foreground = null

        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(),
                itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            itemView.foreground = ResourcesCompat.getDrawable(itemView.resources,
                R.drawable.item_border_line_bottom, null)
            itemView.background = null
            return
        }

        Log.e("dX", "$dX")

        val iconTop: Int
        val iconMargin: Int
        val iconLeft: Int
        val iconRight: Int
        val iconBottom: Int

        if (dX > 0){
            background.color = buyBackgroundColor
            iconTop = itemView.top + (itemHeight - buyIntrinsicHeight) / 2
            iconMargin = (itemHeight - buyIntrinsicHeight) / 2
            iconLeft = itemView.left + iconMargin
            iconRight = itemView.left + iconMargin + buyIntrinsicWidth
            iconBottom = iconTop + buyIntrinsicHeight
            icon = buyIcon!!
            itemView.background = ResourcesCompat.getDrawable(itemView.resources,
                R.drawable.shape_rounded_left, null)
            background.setBounds(itemView.left - dX.toInt(), itemView.top, itemView.right,
                itemView.bottom)
        } else {
            background.color = deleteBackgroundColor
            iconTop = itemView.top + (itemHeight - delIntrinsicHeight) / 2
            iconMargin = (itemHeight - delIntrinsicHeight) / 2
            iconLeft = itemView.right - iconMargin - delIntrinsicWidth
            iconRight = itemView.right - iconMargin
            iconBottom = iconTop + delIntrinsicHeight
            icon = deleteIcon!!
            itemView.background = ResourcesCompat.getDrawable(itemView.resources,
                R.drawable.shape_rounded_right, null)
            background.setBounds(itemView.right + dX.toInt() - 30, itemView.top, itemView.right,
                itemView.bottom)
        }

        background.draw(c)

        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        icon.draw(c)


        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}