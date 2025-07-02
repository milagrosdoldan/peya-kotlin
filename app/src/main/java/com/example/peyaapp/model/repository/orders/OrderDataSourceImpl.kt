package com.example.peyaapp.model.repository.orders


import com.example.peyaapp.model.database.dao.OrderDao
import com.example.peyaapp.model.database.entities.OrderEntity
import com.example.peyaapp.model.database.entities.OrderItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderDao: OrderDao
) : OrderDataSource {

    override suspend fun insertOrder(order: OrderEntity): Long {
        return orderDao.insertOrder(order)
    }

    override suspend fun insertOrderItems(orderItems: List<OrderItemEntity>) {
        orderDao.insertOrderItems(orderItems)
    }

    override fun getAllOrders(): Flow<List<OrderEntity>> {
        return orderDao.getAllOrders()
    }

    override fun getOrderWithItems(orderId: Long): Flow<List<OrderItemEntity>> {
        return orderDao.getOrderItemsByOrderId(orderId)
    }
}
