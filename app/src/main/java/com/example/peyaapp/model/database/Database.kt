package com.example.peyaapp.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.peyaapp.model.database.dao.OrderDao
import com.example.peyaapp.model.database.dao.ProductDao
import com.example.peyaapp.model.database.entities.OrderEntity
import com.example.peyaapp.model.database.entities.OrderItemEntity
import com.example.peyaapp.model.database.entities.ProductEntity

@Database(
    entities = [ProductEntity::class, OrderEntity::class, OrderItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PeyaDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
}