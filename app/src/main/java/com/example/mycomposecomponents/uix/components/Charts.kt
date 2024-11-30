package com.example.mycomposecomponents.uix.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

/**
 * A composable function to draw a Bar Chart with hover effect.
 * @param data List of List<Float> where each sublist represents a group of data points.
 * @param colors List of colors used to paint the bars in the chart.
 */
@Composable
fun BarChart(data: List<List<Float>>, colors: List<Color>) {
    // Calculate averages for each group in the data
    val averages = data.map { group -> group.average().toFloat() }
    // Determine the maximum average for scaling purposes
    val maxAverage = (averages.maxOrNull() ?: 0f).takeIf { it > 0 } ?: 1f
    val barWidth = 80f
    val hoveredBarIndex = remember { mutableIntStateOf(-1) }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
            .pointerInput(Unit) {
                // Detect tap gestures to identify which bar is clicked
                detectTapGestures { tapOffset ->
                    val tappedIndex = ((tapOffset.x - 70f) / (barWidth + 24f)).toInt()
                    if (tappedIndex in averages.indices) {
                        hoveredBarIndex.intValue = tappedIndex
                    }
                }
            }
    ) {
        // Draw Y-axis with arrows
        drawLine(
            color = Color.Black,
            start = Offset(x = 50f, y = 0f),
            end = Offset(x = 50f, y = size.height - 5f),
            strokeWidth = 4f
        )
        drawLine(
            color = Color.Black,
            start = Offset(x = 50f, y = 0f),
            end = Offset(x = 45f, y = 15f),
            strokeWidth = 4f
        )
        drawLine(
            color = Color.Black,
            start = Offset(x = 50f, y = 0f),
            end = Offset(x = 55f, y = 15f),
            strokeWidth = 4f
        )
        // Draw X-axis with arrows
        drawLine(
            color = Color.Black,
            start = Offset(x = 50f, y = size.height - 5f),
            end = Offset(x = size.width - 10f, y = size.height - 5f),
            strokeWidth = 4f
        )
        drawLine(
            color = Color.Black,
            start = Offset(x = size.width - 10f, y = size.height - 5f),
            end = Offset(x = size.width - 25f, y = size.height - 10f),
            strokeWidth = 4f
        )
        drawLine(
            color = Color.Black,
            start = Offset(x = size.width - 10f, y = size.height - 5f),
            end = Offset(x = size.width - 25f, y = size.height + 5f),
            strokeWidth = 4f
        )
        // Draw grid lines and labels on the Y-axis
        for (i in 1..5) {
            val y = size.height - (size.height / 5 * i)
            drawLine(
                color = Color.Gray.copy(alpha = 0.5f),
                start = Offset(x = 50f, y = y),
                end = Offset(x = size.width, y = y),
                strokeWidth = 1f
            )
            drawContext.canvas.nativeCanvas.drawText(
                ((maxAverage / 5) * i).roundToInt().toString(),
                20f,
                y + 5f,
                android.graphics.Paint().apply {
                    textSize = 24f
                    color = android.graphics.Color.BLACK
                    textAlign = android.graphics.Paint.Align.RIGHT
                }
            )
        }
        // Translate to draw the bars
        translate(left = 70f) {
            averages.forEachIndexed { index, average ->
                val barHeight = (average / maxAverage) * size.height
                val isHovered = hoveredBarIndex.intValue == index
                // Draw individual bars with rounded corners
                drawRoundRect(
                    color = if (isHovered) colors[index % colors.size].copy(alpha = 0.5f) else colors[index % colors.size],
                    topLeft = Offset(
                        x = index * (barWidth + 24f),
                        y = size.height - barHeight - 5f
                    ),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(16f, 16f)
                )
                // Add text label for the bar's average value
                drawContext.canvas.nativeCanvas.drawText(
                    average.roundToInt().toString(),
                    (index * (barWidth + 24f) + barWidth / 2),
                    (size.height - barHeight / 2 + 10f),
                    android.graphics.Paint().apply {
                        textSize = 36f
                        color = android.graphics.Color.WHITE
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }
    }
}

/**
 * A composable function to draw a Pie Chart with hover effect.
 * @param data List of Float where each value represents a portion of the total.
 * @param colors List of colors used to paint the slices in the chart.
 */
@Composable
fun PieChart(data: List<Float>, colors: List<Color>) {
    val total = data.sum()
    var startAngle = 0f
    val hoveredSlice = remember { mutableIntStateOf(-1) }

    Canvas(
        modifier = Modifier
            .size(250.dp)
            .padding(16.dp)
            .pointerInput(Unit) {
                // Detect tap gestures to identify which slice is clicked
                detectTapGestures { tapOffset ->
                    val center = Offset(size.width / 2f, size.height / 2f)
                    val tappedAngle = Math.toDegrees(
                        atan2(
                            (tapOffset.y - center.y).toDouble(),
                            (tapOffset.x - center.x).toDouble()
                        )
                    ).toFloat().let { if (it < 0) it + 360 else it }

                    var currentAngle = 0f
                    for (i in data.indices) {
                        val sweepAngle = (data[i] / total) * 360f
                        if (tappedAngle in currentAngle..(currentAngle + sweepAngle)) {
                            hoveredSlice.intValue = i
                            break
                        }
                        currentAngle += sweepAngle
                    }
                }
            }
    ) {
        val minDimension = size.width.coerceAtMost(size.height)
        val radius = minDimension / 2f

        data.forEachIndexed { index, value ->
            val sweepAngle = (value / total) * 360f
            val percentage = ((value / total) * 100).roundToInt()
            val isHovered = hoveredSlice.intValue == index
            val sliceOffset = if (isHovered) 30f else 0f
            val sliceColor = colors[index % colors.size]
            val angleInRadians = Math.toRadians((startAngle + sweepAngle / 2).toDouble())
            val offsetX = cos(angleInRadians).toFloat() * sliceOffset
            val offsetY = sin(angleInRadians).toFloat() * sliceOffset

            // Draw slice shadow for hover effect
            if (isHovered) {
                drawArc(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = Offset(offsetX + 8f, offsetY + 8f),
                    size = Size(size.width, size.height),
                    style = androidx.compose.ui.graphics.drawscope.Fill
                )
            }
            // Draw the slice with gradient effect
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        sliceColor.copy(alpha = 1f),
                        sliceColor.copy(alpha = 0.8f),
                        sliceColor.copy(alpha = 0.5f)
                    )
                ),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(offsetX, offsetY),
                size = Size(size.width, size.height),
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
            // Add percentage label
            val labelX = radius + cos(angleInRadians).toFloat() * radius * 0.7f + offsetX
            val labelY = radius + sin(angleInRadians).toFloat() * radius * 0.7f + offsetY
            drawContext.canvas.nativeCanvas.drawText(
                "$percentage%",
                labelX,
                labelY,
                android.graphics.Paint().apply {
                    textSize = 40f
                    color = android.graphics.Color.WHITE
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
            startAngle += sweepAngle
        }
    }
}

/**
 * A screen displaying both a Bar Chart and a Pie Chart side by side.
 */
@Composable
fun BarChartAndPieChartScreen() {
    val barChartData = listOf(
        listOf(12f, 15f, 20f, 10f),
        listOf(18f, 24f, 16f, 22f),
        listOf(10f, 8f, 12f, 15f),
        listOf(14f, 18f, 16f, 20f),
        listOf(20f, 25f, 30f, 22f),
        listOf(15f, 10f, 18f, 12f),
        listOf(12f, 17f, 14f, 19f),
        listOf(22f, 30f, 28f, 25f)
    )
    val barChartColors = listOf(
        Color(0xFF64B5F6),
        Color(0xFF81C784),
        Color(0xFFFFB74D),
        Color(0xFFE57373),
        Color(0xFF9575CD),
        Color(0xFFBA68C8),
        Color(0xFF4DD0E1),
        Color(0xFFFFD54F)
    )
    val pieChartData = listOf(40f, 25f, 20f, 15f)
    val pieChartColors = listOf(
        Color(0xFF64B5F6),
        Color(0xFF81C784),
        Color(0xFFFFB74D),
        Color(0xFFE57373)
    )
    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFE5D1D0)) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Bar Chart",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF0E0F19)
                )
                BarChart(data = barChartData, colors = barChartColors)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Pie Chart",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF0E0F19)
                )
                PieChart(data = pieChartData, colors = pieChartColors)
            }
        }
    }
}