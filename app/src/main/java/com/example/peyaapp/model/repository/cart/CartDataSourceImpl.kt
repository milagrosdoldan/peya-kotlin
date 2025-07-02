package com.example.peyaapp.model.repository.cart

import com.example.peyaapp.model.data.model.CartItem
import com.example.peyaapp.model.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CartDataSourceImpl @Inject constructor() : CartDataSource {
    private val cartItems = mutableListOf<CartItem>()
    private val _cartItemsFlow = MutableStateFlow<List<CartItem>>(emptyList())
    override val cartItemsFlow: StateFlow<List<CartItem>> get() = _cartItemsFlow

    override fun addToCart(product: Product) {
        val existing = cartItems.find { it.product.id == product.id }
        if (existing != null) {
            existing.quantity += 1
        } else {
            cartItems.add(CartItem(product, 1))
        }
        emitChanges()
    }

    override fun removeProduct(product: Product) {
        cartItems.removeAll { it.product.id == product.id }
        emitChanges()
    }

    override fun updateQuantity(productId: String, quantity: Int) {
        val item = cartItems.find { it.product.id == productId }
        item?.let {
            it.quantity = quantity.coerceAtLeast(1)
            emitChanges()
        }
    }

    override fun getCartItems(): List<CartItem> {
        return cartItems.toList()
    }

    private fun emitChanges() {
        _cartItemsFlow.value = cartItems.map { it.copy() }
    }

    override fun clearCart() {
        _cartItemsFlow.value = emptyList()
    }

}