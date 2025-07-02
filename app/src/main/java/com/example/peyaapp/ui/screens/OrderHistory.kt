package com.example.peyaapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.peyaapp.model.database.entities.OrderEntity
import com.example.peyaapp.viewModels.OrderViewModel
import java.util.Date

@Composable
fun OrderHistory(
    orderViewModel: OrderViewModel
) {

    val orders by orderViewModel.orders.collectAsState()


    return LazyColumn {
        items(orders.size, key = { orders[it].id }) { order ->
            val currentOrder = orders[order]
            OrderItemView(currentOrder)
        }
    }
}

@Composable
fun OrderItemView(order: OrderEntity) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("ID: ${order.id}")
            Text("Total: \$${order.totalAmount}")
            Text("Items: ${order.totalItems}")
            Text("Fecha: ${Date(order.orderDate)}")
        }
    }
}