package com.example.peyaapp.model.repository.product

import android.util.Log
import com.example.peyaapp.model.data.model.Product
import com.example.peyaapp.model.database.dao.ProductDao
import com.example.peyaapp.model.database.entities.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductDataSource {
    private val fakeProducts = listOf(
        Product("product_1", "Hamburguesa", "Con papas y bebida", 1500.0,  "https://images.unsplash.com/photo-1568901346375-23c9450c58cd", "Comida Rápida"),
        Product("product_2", "Pizza", "8 porciones", 2000.0,  "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38", "Comida Rapida"),
        Product("product_3", "Ensalada César", "Incluye aderezo", 1200.0,  "https://images.unsplash.com/photo-1599021419847-d8a7a6aba5b4", "Comida Saludable"),
        Product("product_4", "Tacos", "Tacos al pastor x4", 1100.0,  "https://images.unsplash.com/photo-1683062332605-4e1209d75346", "Comida Mexicana"),
        Product("product_5", "Pollo Teriyaki", "2 piezas con papas", 1400.0,  "https://images.unsplash.com/photo-1645371958635-88dd6c8e1be7", "Comida Japonesa"),
        Product("product_6", "Empanadas", "Carne y queso (4u)", 1000.0, "https://images.unsplash.com/photo-1624128082323-beb6b8b508db", "Comida Rápida"),
        Product("product_7", "Helado", "Vainilla con chocolate", 800.0,  "https://images.unsplash.com/photo-1580915411954-282cb1b0d780", "Postres"),
        Product("product_8","Shawarma", "De carne o pollo", 600.0,  "https://images.unsplash.com/photo-1662116765994-1e4200c43589", "Comida Árabe"),
        Product("product_9", "Wrap de Pollo", "Con vegetales", 1300.0,  "https://images.unsplash.com/photo-1626700051175-6818013e1d4f", "Comida Rápida"),
        Product("product_10", "Sushi", "10 piezas variadas", 2500.0,  "https://images.unsplash.com/photo-1580822184713-fc5400e7fe10", "Comida Japonesa")
    )

    override fun getProducts(): Flow<List<ProductEntity>> {
        Log.d("Room PRODUCTS", "Accediendo a productos...")
        return productDao.getAllProducts()
    }
    override suspend fun insertProducts() {
        val mockProducts = fakeProducts.map { product ->
            ProductEntity(
                id = product.id.toString(),
                name = product.name,
                price = product.price,
                image = product.image,
                category = product.category,
                description = product.description
            )
        }
        productDao.insertProducts(mockProducts)
    }
}