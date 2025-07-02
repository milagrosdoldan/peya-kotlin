// CartViewModel.kt
package com.example.peyaapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peyaapp.model.data.model.CartItem
import com.example.peyaapp.model.data.model.CartState
import com.example.peyaapp.model.data.model.Product
import com.example.peyaapp.model.repository.cart.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel@Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    val cartItems: StateFlow<List<CartItem>> = cartRepository.cartItemsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> get() = _totalPrice
    private val _cartState = MutableStateFlow(CartState())
    val cartState: StateFlow<CartState> get() = _cartState

    private val _uiEvent = Channel<String>()
    val uiEvent = _uiEvent.receiveAsFlow()


    val totalItems: StateFlow<Int> = cartItems
        .map { items -> items.sumOf { it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    init {
        viewModelScope.launch {
            cartItems.collect { items ->
                _totalPrice.value = items.sumOf { it.product.price * it.quantity }
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            _cartState.value = CartState(loading = true, action = "add-${product.id}")
            delay(1000)
            try {
                cartRepository.addToCart(product)
                _cartState.value = CartState(loading = false, action = "")
            } catch (e: Exception) {

            }
            _uiEvent.send("¡Producto agregado al carrito!")
        }
    }

    fun removeFromCart(product: Product) {
        viewModelScope.launch {
            _cartState.value = CartState(loading = true, action = "remove")
            delay(1000)
            cartRepository.removeProduct(product)
        }

    }

    fun updateQuantity(productId: String, quantity: Int) {
        cartRepository.updateQuantity(productId, quantity)
    }

    fun clearCart() {
        viewModelScope.launch {
            _cartState.value = CartState(loading = true, action = "clear")
            delay(1000)
            cartRepository.clearCart()
            _cartState.value = CartState(loading = false, action = "")
        }
    }

}
