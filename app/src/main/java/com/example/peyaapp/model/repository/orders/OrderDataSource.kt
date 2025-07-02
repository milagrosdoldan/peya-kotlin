package com.example.peyaapp.model.repository.orders

import com.example.peyaapp.model.database.entities.OrderEntity
import com.example.peyaapp.model.database.entities.OrderItemEntity
import kotlinx.coroutines.flow.Flow

interface OrderDataSource {
    suspend fun insertOrder(order: OrderEntity): Long
    suspend fun insertOrderItems(orderItems: List<OrderItemEntity>)
    fun getAllOrders(): Flow<List<OrderEntity>>
    fun getOrderWithItems(orderId: Long): Flow<List<OrderItemEntity>>
}
