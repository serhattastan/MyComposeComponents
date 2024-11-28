package com.example.mycomposecomponents.uix.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GenericView(
    movieTitle: String,
    movieDescription: String,
    moviePosterResId: Int,
    castAndCrew: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF010B13))
    ) {
        Image(
            painter = painterResource(id = moviePosterResId),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movieTitle,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE6E6FA),
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = movieDescription,
            color = Color(0xFFB0C4DE),
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Cast & Crew",
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF4682B4),
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f)
                .clipToBounds()
        ) {
            SmoothAutoScrollingContent(castAndCrew)
        }
    }
}

@Composable
fun SmoothAutoScrollingContent(castAndCrew: List<String>) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis =20000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val totalHeight = castAndCrew.size
    val offset = animatedOffset * totalHeight * 40.dp.value

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer { translationY = -offset }
            .clipToBounds(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        infiniteItems(castAndCrew)
    }
}

private fun LazyListScope.infiniteItems(castAndCrew: List<String>) {
    items(Int.MAX_VALUE) { index ->
        val item = castAndCrew[index % castAndCrew.size]
        CreditItem(text = item)
    }
}

@Composable
fun CreditItem(text: String) {
    BasicText(
        text = text,
        style = androidx.compose.ui.text.TextStyle(
            color = Color(0xFF708090),
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        ),
        modifier = Modifier
            .padding(vertical = 8.dp)
    )
}