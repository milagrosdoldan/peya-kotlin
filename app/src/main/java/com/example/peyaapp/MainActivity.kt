package com.example.peyaapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.peyaapp.model.repository.login.LoginRepository
import com.example.peyaapp.navigation.NavGraph
import com.example.peyaapp.ui.PeyaAppTheme
import com.example.peyaapp.ui.activities.LoginActivity
import com.example.peyaapp.viewModels.CartViewModel
import com.example.peyaapp.viewModels.LoginViewModel
import com.example.peyaapp.viewModels.OrderViewModel
import com.example.peyaapp.viewModels.ProductListViewModel
import com.example.peyaapp.viewModels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

// Navigation items
@OptIn(ExperimentalMaterial3Api::class)
data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

val topLevelRoutes = listOf(
    TopLevelRoute("Productos", "productList", Icons.Default.Home),
    TopLevelRoute("Carrito", "cart", Icons.Default.ShoppingCart),
    TopLevelRoute("Perfil", "profile", Icons.Default.Person),
)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            PeyaAppTheme {
                PeyaApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PeyaApp() {
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val productListViewModel : ProductListViewModel = viewModel()
    val orderViewModel : OrderViewModel = viewModel()
    val loginViewModel: LoginViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val cartCount by cartViewModel.totalItems.collectAsState()
    val logoutEvent by loginViewModel.logoutEvent.observeAsState()

    val context = LocalContext.current

    if (logoutEvent == true) {
        LaunchedEffect(Unit) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            (context as? Activity)?.finish()
        }
    }

    val currentTitle = when (currentRoute) {
        "productList" -> "Productos"
        "cart" -> "Carrito"
        "profile" -> "Perfil"
        "order-records" -> "Historial de Pedidos"
        else -> "Peya App"
    }

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            ModernTopAppBar(
                title = currentTitle,
                showCartIcon = currentRoute == "productList",
                showBackButton = currentRoute == "cart",
                onBackClick = { navController.popBackStack() },
                onCartClick = {
                    navController.navigate("cart") {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                cartCount = cartCount,
                showOrdersIcon = currentRoute == "profile",
                onShowOrdersIcon = {
                    navController.navigate("order-records") {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                onLogoutUser = { loginViewModel.logout() }
            )
        },
        bottomBar = {
            NavigationBar {
                topLevelRoutes.forEach { topLevelRoute ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = topLevelRoute.icon,
                                contentDescription = topLevelRoute.name
                            )
                        },
                        label = { Text(topLevelRoute.name) },
                        selected = currentRoute == topLevelRoute.route,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            NavGraph(
                navController = navController,
                cartViewModel = cartViewModel,
                profileViewModel = profileViewModel,
                productListViewModel = productListViewModel,
                orderViewModel = orderViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ModernTopAppBar(
    title: String,
    showCartIcon: Boolean = false,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    cartCount : Int,
    showOrdersIcon : Boolean = false,
    onShowOrdersIcon: () -> Unit = {},
    onLogoutUser: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.White,
        shadowElevation = 2.dp,
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            navigationIcon = {
                if (showBackButton) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Volver",
                            tint = Color.Black
                        )
                    }
                }
            },
            actions = {
                if (showCartIcon) {
                    IconButton(onClick = onCartClick) {
                        BadgedBox(
                            badge = {
                                if (cartCount > 0) {
                                    Badge {
                                        Text(cartCount.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Carrito",
                                tint = Color.Black
                            )
                        }
                    }
                }
                if(showOrdersIcon) {
                    IconButton(
                        onClick = onShowOrdersIcon
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = "Historial de Pedidos",
                            tint = Color.Black
                        )
                    }
                    IconButton(
                        onClick = onLogoutUser
                    ) {
                        Icon(
                            imageVector = Icons.Default.Output,
                            contentDescription = "Logout",
                            tint = Color.Black
                        )
                    }
                }
            },
            colors = centerAlignedTopAppBarColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier.height(64.dp)
        )
    }
}
