package com.example.peyaapp.model.repository.cart

import com.example.peyaapp.model.data.model.CartItem
import com.example.peyaapp.model.data.model.Product
import kotlinx.coroutines.flow.StateFlow

interface CartDataSource {
    fun addToCart(product: Product)
    fun removeProduct(product: Product)
    fun updateQuantity(productId: String, quantity: Int)
    fun getCartItems(): List<CartItem>
    val cartItemsFlow: StateFlow<List<CartItem>>
    fun clearCart()
}