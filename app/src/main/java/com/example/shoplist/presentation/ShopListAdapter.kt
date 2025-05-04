package com.example.shoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.model.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {

    var shopList = listOf<ShopItem>()

    var count = 0

    var onShopItemLongClickListener : ((ShopItem) -> Unit)? = null

    class ShopListViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val tv_name = view.findViewById<TextView>(R.id.tv_name)
        val tv_count = view.findViewById<TextView>(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        Log.d("My Log", "onCreateViewHolder: ${++count}")
        val layout = when (viewType) {
            ITEM_ENABLED ->R.layout.item_shop_enabled
            ITEM_DISABLED->R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type : $viewType")
        }
        val view = LayoutInflater.from(parent.context).
            inflate(
                layout,
                parent,
                false
            )
        return ShopListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = shopList[position]
        return if (shopItem.enabled){
            ITEM_ENABLED
        }else {
            ITEM_DISABLED
        }
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.tv_name.text = shopItem.name
        holder.tv_count.text = shopItem.count.toString()
        holder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
    }

    interface OnShopItemLongClickListener {
        fun onShopItemLongClick(shopItem: ShopItem)
    }

    companion object {

        const val ITEM_ENABLED = 1
        const val ITEM_DISABLED = 0

        const val MAX_POOL_SIZE = 15
    }
}