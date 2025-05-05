package com.example.shoplist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R


class ShopListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tv_name = view.findViewById<TextView>(R.id.tv_name)
    val tv_count = view.findViewById<TextView>(R.id.tv_count)
}