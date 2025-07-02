package com.example.peyaapp.ui.screens
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.peyaapp.ui.components.CartItemRow
import com.example.peyaapp.viewModels.CartViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay
import androidx.compose.runtime.*
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.peyaapp.ui.components.EmptyScreen
import com.example.peyaapp.viewModels.OrderViewModel

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = viewModel(),
    navController: NavHostController,
    orderViewModel: OrderViewModel = viewModel()
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val total by cartViewModel.totalPrice.collectAsState()
    val cartState by cartViewModel.cartState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(true) {
        orderViewModel.uiEvent.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        if (cartItems.isEmpty()) {
            EmptyScreen(
                icon = Icons.Default.RemoveShoppingCart,
                title = "Tu carrito está vacío",
                buttonText ="Ver productos",
                buttonOnClick= {navController.navigate("productList")}

            )
        } else {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(
                    items = cartItems,
                    key = { it.product.id }
                ) { cartItem ->
                    CartItemRow(
                        cartItem = cartItem,
                        onQuantityChange = { newQty ->
                            cartViewModel.updateQuantity(cartItem.product.id, newQty)
                        },
                        onRemoveItem = {
                            cartViewModel.removeFromCart(cartItem.product)
                        },
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 2.dp
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Subtotal:",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = "$${"%.2f".format(total)}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Button(
                    onClick = {
                        orderViewModel.placeOrder(cartItems)
                        cartViewModel.clearCart()
                    },
                    enabled = (!(cartState.action == "remove" && cartState.loading) || !cartState.loading && cartState.action == "clear"),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    if(cartState.loading && cartState.action == "clear") {
                        Row {
                            TypingDots()
                        }
                    } else{
                        Text(
                            text = "Comprar",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TypingDots() {
    var dotCount by remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
        while (true) {
            dotCount = (dotCount % 3) + 1
            delay(500)
        }
    }

    Row {
        repeat(dotCount) {
            Icon(
                imageVector = Icons.Default.Circle,
                contentDescription = "Typing Dot",
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .padding(end = 0.5.dp)
                    .then(Modifier)
            )
        }
    }
}