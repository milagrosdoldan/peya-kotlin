package com.example.peyaapp.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peyaapp.model.data.model.Product
import com.example.peyaapp.model.data.model.ProductListState
import com.example.peyaapp.model.repository.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.first

@HiltViewModel
class ProductListViewModel@Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel(){
    private val _productState = MutableStateFlow(ProductListState(loading = true, products = emptyList()))
    var productState = _productState.asStateFlow()

    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    var filteredProducts : StateFlow<List<Product>> = _filteredProducts

    var searchQuery = mutableStateOf("")

    private val exceptionHandler = CoroutineExceptionHandler { _, exception  ->
        _productState.value = ProductListState(error = exception as Exception, loading = false, products = emptyList())
    }
    init {

        try {
            viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                var products = productRepository.getProducts().first()

                if (products.isEmpty()) {
                    productRepository.insertProducts()
                    products = productRepository.getProducts().first()
                }

                val productList = products.map { entity ->
                    Product(
                        id = entity.id,
                        name = entity.name,
                        description = entity.description,
                        price = entity.price,
                        image = entity.image,
                        category = entity.category
                    )
                }

                _productState.value = ProductListState(loading = false, products = productList)
                _filteredProducts.value = _productState.value.products
            }
        } catch (e: Exception) {
            ProductListState(error = e as Exception, loading = false, products = emptyList())
        }
    }


        fun onSearchQueryChange(query: String) {
            searchQuery.value = query
            _filteredProducts.value = if (query.isEmpty()) {
                _productState.value.products
            } else {
                _productState.value.products.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.description.contains(query, ignoreCase = true)
                }
            }

    }
}
