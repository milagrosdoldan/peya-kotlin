package com.example.peyaapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.peyaapp.model.data.model.Product
import com.example.peyaapp.ui.components.ProductItem
import com.example.peyaapp.viewModels.CartViewModel
import com.example.peyaapp.viewModels.ProductListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productListViewModel: ProductListViewModel,
    onAddToCart: (Product) -> Unit,
    cartViewModel: CartViewModel
) {
    val searchQuery = productListViewModel.searchQuery.value
    val productState by productListViewModel.productState.collectAsState()
    val productList by productListViewModel.filteredProducts.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(true) {
        cartViewModel.uiEvent.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = if (productState.loading) Arrangement.Center else Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

            Spacer(modifier = Modifier.height(16.dp))

            if (productState.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Cargando Productos...",
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
            } else {
                SearchOutlinedTextField(
                value = searchQuery,
                onValueChange = { productListViewModel.onSearchQueryChange(it) },
                label = "Buscar productos...",
                modifier = Modifier.fillMaxWidth()
            )
                Spacer(modifier = Modifier.height(16.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(productList.size) { product ->
                        val product = productList[product]
                        ProductItem(
                            product = product,
                            onAddToCart = { onAddToCart(product) },
                            cartViewModel = cartViewModel
                        )
                    }
                }
            }
        }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 4.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = label,
                    color = Color.Gray.copy(alpha = 0.6f)
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = Color.Gray
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
