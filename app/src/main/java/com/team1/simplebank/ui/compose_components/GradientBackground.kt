package com.team1.simplebank.ui.compose_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.team1.simplebank.colors_for_composable.BlueLightPastel
import com.team1.simplebank.colors_for_composable.WhiteBlueLight

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    startColor: Color = BlueLightPastel,
    midColor: Color = WhiteBlueLight,
    endColor: Color = BlueLightPastel,
    content: @Composable BoxScope.() -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(startColor, midColor, endColor),
                        start = Offset.Zero,
                        end = Offset(width, height)
                    )
                )
        ) {
            content()
        }
    }
}

