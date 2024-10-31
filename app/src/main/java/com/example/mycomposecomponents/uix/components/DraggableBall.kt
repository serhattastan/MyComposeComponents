package com.example.mycomposecomponents.uix.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * A composable function that displays a draggable ball that can be moved across the screen
 * and snaps to the left or right edge based on its position when released.
 *
 * @param painter The image painter used for displaying the ball.
 */
@Composable
fun DraggableBall(
    painter: Painter // Accepts an image painter as parameter
) {
    // Dynamically get screen dimensions
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp

    // Convert dp to px for screen dimensions
    val density = LocalDensity.current
    val screenWidthPx = with(density) { screenWidthDp.toPx() }
    val screenHeightPx = with(density) { screenHeightDp.toPx() }
    val horizontalMarginPx = with(density) { 10.dp.toPx() } // Left-right margin for edge snapping
    val verticalMarginPx = with(density) { 30.dp.toPx() } // Top-bottom margin for edge snapping

    // Animatable value for horizontal dragging with animations
    val animatedOffsetX = remember { Animatable(horizontalMarginPx) }
    var offsetY by remember { mutableFloatStateOf(verticalMarginPx) } // Vertical position

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            // Determine target position to snap to based on horizontal position
                            val targetX = if (animatedOffsetX.value > screenWidthPx / 2) {
                                screenWidthPx - 80.dp.toPx() - horizontalMarginPx // Snap to right
                            } else {
                                horizontalMarginPx // Snap to left
                            }

                            // Calculate animation duration based on distance
                            val distance = abs(targetX - animatedOffsetX.value)
                            val duration = (distance / screenWidthPx * 1000).toInt() // Proportional to screen width

                            // Animate to target position
                            animatedOffsetX.animateTo(
                                targetValue = targetX,
                                animationSpec = tween(durationMillis = duration)
                            )
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume() // Consume gesture

                        coroutineScope.launch {
                            // Update horizontal position with boundary check
                            val newX = (animatedOffsetX.value + dragAmount.x)
                                .coerceIn(horizontalMarginPx, screenWidthPx - 80.dp.toPx() - horizontalMarginPx)
                            animatedOffsetX.snapTo(newX)

                            // Update vertical position directly with boundary check
                            offsetY = (offsetY + dragAmount.y)
                                .coerceIn(verticalMarginPx, screenHeightPx - 80.dp.toPx() - verticalMarginPx)
                        }
                    }
                )
            }
    ) {
        Image(
            painter = painter, // Uses the provided image painter
            contentDescription = "Draggable Ball",
            modifier = Modifier
                .offset {
                    IntOffset(
                        animatedOffsetX.value.roundToInt(),
                        offsetY.roundToInt()
                    )
                }
                .size(80.dp) // Ball size
        )
    }
}