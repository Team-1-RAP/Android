package com.team1.simplebank.ui.compose_components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.team1.simplebank.colors_for_composable.BlueNormal

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
){

    val maxOffset = 10f
    val dotSize = 16.dp
    val delayUnit = 300

    Box(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        @Composable
        fun Dot(
            offset: Float
        ) = Spacer(
            Modifier
                .size(dotSize)
                .offset(y = -offset.dp)
                .background(
                    color = BlueNormal,
                    shape = CircleShape
                )
        )

        val infiniteTransition = rememberInfiniteTransition(label = "")

        @Composable
        fun animateOffsetWithDelay(delay: Int) = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = delayUnit * 4
                    0f at delay with LinearEasing
                    maxOffset at delay + delayUnit with LinearEasing
                    0f at delay + delayUnit * 2
                }
            ), label = ""
        )

        val offset1 by animateOffsetWithDelay(0)
        val offset2 by animateOffsetWithDelay(delayUnit)
        val offset3 by animateOffsetWithDelay(delayUnit * 2)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = maxOffset.dp)
        ) {
            val spaceSize = 3.dp

            Dot(offset1)
            Spacer(Modifier.width(spaceSize))
            Dot(offset2)
            Spacer(Modifier.width(spaceSize))
            Dot(offset3)
        }
    }




}

