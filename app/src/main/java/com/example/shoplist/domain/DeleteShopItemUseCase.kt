package com.example.shoplist.domain

import com.example.shoplist.domain.model.ShopItem
import com.example.shoplist.domain.repository.ShopListRepository

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository){

    fun deleteShopItem(shopItem : ShopItem){
        shopListRepository.deleteShopItem(shopItem)
    }
}