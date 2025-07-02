package com.example.peyaapp.model.data.model

data class CartItem(
    val product: Product,
    var quantity: Int = 1
)

data class CartState(
    val loading : Boolean = false,
    val cartItems: List<CartItem> = emptyList(),
    val error: Exception? = null,
    val action : String? = null
)