package com.example.shoplist.domain.repository

import androidx.lifecycle.LiveData
import com.example.shoplist.domain.model.ShopItem

interface ShopListRepository {

    fun addShopItem(item : ShopItem)

    fun editShopItem(shopItem: ShopItem)

    fun getShopItem(shopItemId : Int) : ShopItem

    fun deleteShopItem(shopItem : ShopItem)

    fun getShopList() : LiveData<List<ShopItem>>
}