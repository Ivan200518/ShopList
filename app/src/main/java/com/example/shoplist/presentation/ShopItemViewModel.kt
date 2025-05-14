package com.example.shoplist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.AddShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemUseCase
import com.example.shoplist.domain.model.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)

    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val addShopItemUseCase = AddShopItemUseCase(repository)

    private val _errorMessageName = MutableLiveData<Boolean>()
    val errorMessageName: LiveData<Boolean>
        get() = _errorMessageName

    private val _errorMessageCount = MutableLiveData<Boolean>()
    val errorMessageCount: LiveData<Boolean>
        get() = _errorMessageCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem : LiveData<ShopItem>
        get() = _shopItem

    private val _isReadyToClose =MutableLiveData<Unit>()
    val isReadyToCloseScreen : LiveData<Unit>
        get() = _isReadyToClose

    fun getItem(shopItemId: Int) {
        _shopItem.value = getShopItemUseCase.getShopItem(shopItemId)
    }



    fun editItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsIsValid = validateInput(name, count)
        if (fieldsIsValid) {
            _shopItem.value?.let {
                val item = it.copy(name = name,count = count)
                editShopItemUseCase.editShopItem(item)
                closeScreen()
            }

        }
    }

    fun addItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsIsValid = validateInput(name, count)
        if (fieldsIsValid) {
            val item = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(item)
            closeScreen()
        }
    }

    private fun closeScreen() {
        _isReadyToClose.value = Unit
    }


    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            result = false
            _errorMessageName.value = true
        }
        if (count <= 0) {
            // TODO: print error if count <= 0
            result = false
            _errorMessageCount.value = true
        }
        return result
    }

    fun resetErrorMessageName() {
        _errorMessageName.value = false
    }

    fun resetErrorMessageCount() {
        _errorMessageCount.value = false
    }
}