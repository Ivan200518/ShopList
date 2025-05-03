package com.example.shoplist.domain

import com.example.shoplist.domain.model.ShopItem
import com.example.shoplist.domain.repository.ShopListRepository

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(shopItemId : Int) : ShopItem {
        return  shopListRepository.getShopItem(shopItemId)
    }
}