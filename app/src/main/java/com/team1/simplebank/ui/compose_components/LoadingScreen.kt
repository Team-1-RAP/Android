package com.team1.simplebank.ui.compose_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
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
    Box(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        CircularProgressIndicator(
            modifier = modifier.size(32.dp),
            color = BlueNormal,
            trackColor = Color.White,
            strokeWidth = 4.dp,
            strokeCap = StrokeCap.Round,
        )
    }
}