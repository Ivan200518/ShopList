package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.model.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ShopItemViewModel
    private lateinit var tilName : TextInputLayout
    private lateinit var tilCount : TextInputLayout
    private lateinit var edName : EditText
    private lateinit var edCount : EditText
    private lateinit var buttonSave : Button
    private var shopItemId : Int = ShopItem.UNDEFINED_ID
    private var screenMode = UNKNOWN_MODE
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
        viewModel = ViewModelProvider(this) [ShopItemViewModel::class.java]
        initViews()
        launchRightMode()
        addTextChangeListeners()
        observeErrors()
        observeFinishScreen()
    }

    private fun observeFinishScreen() {
        viewModel.isReadyToCloseScreen.observe(this) {
            finish()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            ADD_MODE -> launchAddMode()
            EDIT_MODE -> launchEditMode()
        }
    }

    private fun observeErrors() {
        viewModel.errorMessageName.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message
        }
        viewModel.errorMessageCount.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_count)

            } else {
                null
            }
            tilCount.error = message
        }
    }

    private fun addTextChangeListeners() {
        edName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorMessageName()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        edCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorMessageCount()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            val name = edName.text.toString()
            val count = edCount.text.toString()
            viewModel.addItem(name,count)
        }




    }


    private fun launchEditMode() {
        viewModel.getItem(shopItemId)
        viewModel.shopItem.observe(this){
            edName.setText(it.name)
            edCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            val name = edName.text.toString()
            val count = edCount.text.toString()
            viewModel.editItem(name,count)
        }
    }
    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)){
            throw RuntimeException("Don't have param  extra screen mode")
          }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)

        if (mode != ADD_MODE && mode != EDIT_MODE){
            throw RuntimeException("Extra mode is unknown $mode ")
        }

        screenMode = mode

        if (screenMode == EDIT_MODE) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)){
                throw RuntimeException("Param item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun initViews() {
        tilName = findViewById(R.id.til_name)
        tilCount = findViewById(R.id.til_count)
        edName = findViewById(R.id.et_name)
        edCount = findViewById(R.id.et_count)
        buttonSave = findViewById(R.id.save_button)
    }



    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val EDIT_MODE = "edit_mode"
        private const val ADD_MODE = "add_mode"
        private const val UNKNOWN_MODE = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, ADD_MODE)
            return intent
        }
        
        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, EDIT_MODE)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}