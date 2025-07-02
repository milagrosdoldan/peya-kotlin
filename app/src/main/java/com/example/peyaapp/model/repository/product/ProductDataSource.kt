package com.example.peyaapp.model.repository.product

import com.example.peyaapp.model.database.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductDataSource {
    fun getProducts(): Flow<List<ProductEntity>>
    suspend fun insertProducts()
}
