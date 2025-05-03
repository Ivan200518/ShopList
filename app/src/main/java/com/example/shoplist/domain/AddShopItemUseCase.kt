package com.example.shoplist.domain

import com.example.shoplist.domain.model.ShopItem
import com.example.shoplist.domain.repository.ShopListRepository

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun addShopItem(item : ShopItem) {
        shopListRepository.addShopItem(item)
    }
}