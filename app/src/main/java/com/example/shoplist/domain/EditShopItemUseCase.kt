package com.example.shoplist.domain

import com.example.shoplist.domain.model.ShopItem
import com.example.shoplist.domain.repository.ShopListRepository

class EditShopItemUseCase(private val shopListRepository: ShopListRepository){

    fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}