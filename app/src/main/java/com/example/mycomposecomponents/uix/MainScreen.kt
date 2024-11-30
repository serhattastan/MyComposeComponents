package com.example.mycomposecomponents.uix

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.mycomposecomponents.uix.components.BarChartAndPieChartScreen

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // İçeriği merkeze hizala
    ) {

        val data = listOf(
            listOf(12f, 15f, 20f, 10f),
            listOf(18f, 24f, 16f, 22f),
            listOf(10f, 8f, 12f, 15f),
            listOf(14f, 18f, 16f, 20f)
        )
        val colors = listOf(Color(0xFF6200EE), Color(0xFF03DAC5), Color(0xFFBB86FC), Color(0xFF3700B3))

        BarChartAndPieChartScreen()

    }

//    GenericView(
//        movieTitle = "Inception",
//        movieDescription = "A skilled thief, the absolute best in the dangerous art of extraction, stealing valuable secrets from deep within the subconscious during the dream state.",
//        moviePosterResId = R.drawable.inception, // Replace with your drawable resource
//        castAndCrew = listOf(
//            "Director: Christopher Nolan",
//            "Producer: Emma Thomas",
//            "Actor: Leonardo DiCaprio",
//            "Actor: Joseph Gordon-Levitt",
//            "Actor: Ellen Page",
//            "Cinematography: Wally Pfister",
//            "Editor: Lee Smith",
//            "Composer: Hans Zimmer",
//            "Production Designer: Guy Hendrix Dyas",
//            "Visual Effects Supervisor: Paul Franklin",
//            "Sound Designer: Richard King",
//            "Casting Director: John Papsidera",
//            "Costume Designer: Jeffrey Kurland"
//        )
//    )

}