package com.example.peyaapp.model.database.dao

import androidx.room.*
import com.example.peyaapp.model.database.entities.OrderEntity
import com.example.peyaapp.model.database.entities.OrderItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    @Transaction
    suspend fun insertFullOrder(order: OrderEntity, items: List<OrderItemEntity>) {
        val orderId = insertOrder(order)
        val itemsWithOrderId = items.map {
            it.copy(orderId = orderId.toInt())
        }
        insertOrderItems(itemsWithOrderId)
    }

    @Query("SELECT * FROM orders ORDER BY orderDate DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    fun getOrderItemsByOrderId(orderId: Long): Flow<List<OrderItemEntity>>
}
