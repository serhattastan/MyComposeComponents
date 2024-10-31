package com.example.mycomposecomponents.uix

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.mycomposecomponents.R
import com.example.mycomposecomponents.uix.components.DraggableBall

@Composable
fun DraggableBallScreen() {
    Box(
        modifier = Modifier.fillMaxSize()) {
        DraggableBall(
            painter = painterResource(id = R.drawable.ic_android)
        )
    }
}