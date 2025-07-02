package com.example.peyaapp.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

val RedPrimary = Color(0xFFE31B23)
val RedDark = Color(0xFFB00020)
val RedLight = Color(0xFFFFCDD2)

val BackgroundLight = Color(0xFFFFFFFF)
val SurfaceLight = Color(0xFFF6F6F6)
val OnPrimary = Color.White
val OnBackground = Color.Black



private val LightColors = lightColorScheme(
    primary = RedPrimary,
    onPrimary = OnPrimary,
    secondary = RedLight,
    onSecondary = Color.White,
    background = BackgroundLight,
    onBackground = OnBackground,
    surface = SurfaceLight,
    onSurface = Color.Black,
    error = RedDark,
    onError = Color.White,
)

@Composable
fun PeyaAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}
