package com.example.loaders.loaderClasses

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RectangularLoader(
    width: Dp,
    height: Dp,
    cornerRadius: Float = 32f,
    stroke: Dp = 4.dp,
    outerRectColor: Color = Color.Blue,
    innerRectColor: Color = Color.Blue.copy(alpha = 0.2f),
    animDuration: Int = 1000
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = animDuration),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    Box(
        modifier = Modifier
            .size(width, height)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(width, height),
            onDraw = {
                val path = Path().apply {
                    moveTo(cornerRadius, 0f)
                    lineTo(width.toPx() - cornerRadius, 0f)
                    arcTo(
                        Rect(
                            Offset(width.toPx() - cornerRadius * 2, 0f),
                            Offset(width.toPx(), cornerRadius * 2)
                        ),
                        -90f,
                        90f,
                        false
                    )
                    lineTo(width.toPx(), height.toPx() - cornerRadius)
                    arcTo(
                        Rect(
                            Offset(
                                width.toPx() - cornerRadius * 2,
                                height.toPx() - cornerRadius * 2
                            ),
                            Offset(width.toPx(), height.toPx())
                        ), 0f, 90f, false
                    )
                    lineTo(cornerRadius, height.toPx())
                    arcTo(
                        Rect(
                            Offset(0f, height.toPx() - cornerRadius * 2),
                            Offset(cornerRadius * 2, height.toPx())
                        ),
                        90f,
                        90f,
                        false
                    )
                    lineTo(0f, cornerRadius)
                    arcTo(
                        Rect(Offset(0f, 0f), Offset(cornerRadius * 2, cornerRadius * 2)),
                        180f,
                        90f,
                        false
                    )
                    close()
                }

                val pathMeasure = PathMeasure()
                pathMeasure.setPath(path, false)

                val length = pathMeasure.length
                val progressLength = progress.value * length

                val segment = Path()
                pathMeasure.getSegment(0f, progressLength, segment, true)

                drawIntoCanvas { canvas ->
                    canvas.drawPath(
                        path = path,
                        paint = Paint().apply {
                            color = innerRectColor
                            style = PaintingStyle.Stroke
                            strokeWidth = stroke.toPx()
                        }
                    )
                    canvas.drawPath(
                        path = segment,
                        paint = Paint().apply {
                            color = outerRectColor
                            style = PaintingStyle.Stroke
                            strokeWidth = stroke.toPx()
                        }
                    )
                }
            }
        )
    }
}
