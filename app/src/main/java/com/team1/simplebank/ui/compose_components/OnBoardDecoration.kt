package com.team1.simplebank.ui.compose_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.team1.simplebank.R

@Composable
fun OnBoardDecoration() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            modifier = Modifier.fillMaxWidth()
                .offset(y = (-2).dp)
                .align(Alignment.TopEnd),
            painter = painterResource(R.drawable.decoration1),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (2).dp)
                .graphicsLayer(
                    scaleX = -1f,
                    scaleY = -1f
                )
                .align(Alignment.BottomStart),
            painter = painterResource(R.drawable.decoration1),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )
    }
}