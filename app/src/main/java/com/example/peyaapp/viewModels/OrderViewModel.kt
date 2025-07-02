package com.example.peyaapp.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peyaapp.model.database.entities.OrderEntity
import com.example.peyaapp.model.database.entities.OrderItemEntity
import com.example.peyaapp.model.repository.orders.OrderRepository
import com.example.peyaapp.model.data.model.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    private val _orders = MutableStateFlow<List<OrderEntity>>(emptyList())
    val orders = _orders.asStateFlow()

    private val _uiEvent = Channel<String>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            val allOrders = orderRepository.getAllOrders().first()
            _orders.value = allOrders
        }
    }
    fun placeOrder(cartItems: List<CartItem>) {
        viewModelScope.launch {
            val totalAmount = cartItems.sumOf { it.product.price * it.quantity }
            val totalItems = cartItems.sumOf { it.quantity }

            val orderEntity = OrderEntity(
                totalAmount = totalAmount,
                totalItems = totalItems
            )

            val orderId = orderRepository.insertOrder(orderEntity)

            val orderItems = cartItems.map {
                OrderItemEntity(
                    orderId = orderId.toInt(),
                    productId = it.product.id,
                    quantity = it.quantity,
                    price = it.product.price
                )
            }

            orderRepository.insertOrderItems(orderItems)
            _uiEvent.send("Compra realizada con éxito")

        }
    }
}
