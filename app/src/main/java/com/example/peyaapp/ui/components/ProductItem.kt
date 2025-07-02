package com.example.peyaapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.peyaapp.model.data.model.Product
import com.example.peyaapp.viewModels.CartViewModel

@Composable
fun ProductItem(
    product: Product,
    onAddToCart: () -> Unit,
    cartViewModel: CartViewModel
) {
    val cartState by cartViewModel.cartState.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        shape = RoundedCornerShape(12.dp),

    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadImage(
                url = product.image,
                contentDescription = product.name,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF222222),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF666666),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFFE31B23)
                )

                Button(
                    onClick = onAddToCart,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE31B23),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFf3f2f4)
                    ),
                    enabled = !cartState.loading,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(30.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    if (cartState.action?.contains(product.id.toString()) == true) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(17.dp),
                            color = Color.Gray,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Agregar al carrito",
                            modifier = Modifier.size(17.dp)
                        )
                    }
                }
            }
        }
    }
}
