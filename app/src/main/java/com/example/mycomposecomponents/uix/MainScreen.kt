package com.example.mycomposecomponents.uix

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mycomposecomponents.R
import com.example.mycomposecomponents.uix.components.GenericView

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // İçeriği merkeze hizala
    ) {
        GenericView(
            movieTitle = "Inception",
            movieDescription = "A skilled thief, the absolute best in the dangerous art of extraction, stealing valuable secrets from deep within the subconscious during the dream state.",
            moviePosterResId = R.drawable.inception, // Replace with your drawable resource
            castAndCrew = listOf(
                "Director: Christopher Nolan",
                "Producer: Emma Thomas",
                "Actor: Leonardo DiCaprio",
                "Actor: Joseph Gordon-Levitt",
                "Actor: Ellen Page",
                "Cinematography: Wally Pfister",
                "Editor: Lee Smith",
                "Composer: Hans Zimmer",
                "Production Designer: Guy Hendrix Dyas",
                "Visual Effects Supervisor: Paul Franklin",
                "Sound Designer: Richard King",
                "Casting Director: John Papsidera",
                "Costume Designer: Jeffrey Kurland"
            )
        )
    }
}