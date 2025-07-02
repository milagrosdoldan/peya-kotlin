// FakeCartRepository.kt
package com.example.peyaapp.model.repository.cart

import com.example.peyaapp.model.data.model.CartItem
import com.example.peyaapp.model.data.model.Product
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class CartRepository @Inject constructor(
    private val cartDataSource: CartDataSource
) {
    val cartItemsFlow: StateFlow<List<CartItem>> = cartDataSource.cartItemsFlow

    fun addToCart(product: Product) = cartDataSource.addToCart(product)

    fun removeProduct(product: Product) = cartDataSource.removeProduct(product)

    fun updateQuantity(productId: String, quantity: Int) = cartDataSource.updateQuantity(productId, quantity)

    fun getCartItems(): List<CartItem> = cartDataSource.getCartItems()

    fun clearCart() = cartDataSource.clearCart()
}
