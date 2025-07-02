package com.example.peyaapp.model.repository.product

import com.example.peyaapp.model.database.entities.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productDataSource: ProductDataSource
) {
    fun getProducts(): Flow<List<ProductEntity>> = productDataSource.getProducts()
    suspend fun insertProducts() : Unit = productDataSource.insertProducts()
}
