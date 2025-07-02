package com.example.peyaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.peyaapp.ui.screens.CartScreen
import com.example.peyaapp.ui.screens.OrderHistory
import com.example.peyaapp.ui.screens.ProductListScreen
import com.example.peyaapp.ui.screens.UserProfile
import com.example.peyaapp.viewModels.CartViewModel
import com.example.peyaapp.viewModels.OrderViewModel
import com.example.peyaapp.viewModels.ProductListViewModel
import com.example.peyaapp.viewModels.ProfileViewModel


@Composable
fun NavGraph(
    cartViewModel: CartViewModel,
    navController: NavHostController,
    profileViewModel:ProfileViewModel,
    productListViewModel: ProductListViewModel,
    orderViewModel : OrderViewModel,

) {

    NavHost(navController = navController, startDestination = "productList") {
        composable("productList") {
            ProductListScreen(
                onAddToCart = { product ->
                    cartViewModel.addToCart(product)
                },
                cartViewModel = cartViewModel,
                productListViewModel = productListViewModel
            )
        }

        composable("cart") {
            CartScreen(
                cartViewModel = cartViewModel,
                navController = navController,
                orderViewModel = orderViewModel
            )
        }
        composable("profile") {
            UserProfile(
                profileViewModel
            )
        }
        composable("order-records") {
            OrderHistory(orderViewModel = orderViewModel)
        }
    }
}
