package com.example.mycomposecomponents.uix

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mycomposecomponents.uix.components.InfiniteAnimation

@Composable
fun MainScreen() {
    // Ekranın ortasında InfiniteAnimation composable'ını gösteriyoruz
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // İçeriği merkeze hizala
    ) {
        DraggableBallScreen() // InfiniteAnimation composable'ı burada çağrılıyor
    }
}