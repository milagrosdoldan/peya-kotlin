package com.example.peyaapp.model.repository.orders

import com.example.peyaapp.model.database.entities.OrderEntity
import com.example.peyaapp.model.database.entities.OrderItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val orderDataSource: OrderDataSource
) {

    suspend fun insertOrder(order: OrderEntity): Long {
        return orderDataSource.insertOrder(order)
    }

    suspend fun insertOrderItems(orderItems: List<OrderItemEntity>) {
        orderDataSource.insertOrderItems(orderItems)
    }

    fun getAllOrders(): Flow<List<OrderEntity>> {
        return orderDataSource.getAllOrders()
    }

    fun getOrderWithItems(orderId: Long): Flow<List<OrderItemEntity>> {
        return orderDataSource.getOrderWithItems(orderId)
    }
}
