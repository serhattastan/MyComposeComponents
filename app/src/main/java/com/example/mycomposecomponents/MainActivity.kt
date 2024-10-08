package com.example.mycomposecomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mycomposecomponents.ui.theme.MyComposeComponentsTheme
import com.example.mycomposecomponents.uix.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyComposeComponentsTheme {
                MainScreen()
            }
        }
    }
}
