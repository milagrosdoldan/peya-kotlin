package com.example.peyaapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@Composable
fun LoadImage(
    url: String,
    contentDescription: String,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {

    return AsyncImage(
        model = url,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}